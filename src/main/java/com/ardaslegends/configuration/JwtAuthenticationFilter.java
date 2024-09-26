package com.ardaslegends.configuration;

import com.ardaslegends.presentation.api.PlayerRestController;
import com.ardaslegends.service.authentication.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("Processing authentication for '{}'", request.getRequestURL());
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            log.debug("JWT: {}", jwt);
            final String userDiscordId = jwtUtil.extractDiscordIdFromJWT(jwt);
            log.debug("User Discord ID: {}", userDiscordId);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userDiscordId != null && authentication == null) {
                //if (userDiscordId != null) {
                try {
                    boolean isTokenValid = jwtUtil.isTokenValid(jwt);
                    log.debug("Token is valid: {}", isTokenValid);
                    if (jwtUtil.isTokenValid(jwt)) {
                        try {
                            log.debug("Loading user by username to get UserDetails");
                            // load user by username to get UserDetails
                            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDiscordId);
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        } catch (Exception e) {
                            // if user does not exist and we are in the post player endpoint, we can create a new user
                            // instead of throwing an exception
                            // if we are not in the /register endpoint, we should throw an exception
                            if (request.getRequestURI().equals(PlayerRestController.BASE_URL) && request.getMethod().equals("POST")) {
                                log.info("User does not exist, but we are in the /register endpoint, so we will create a new user");
                                filterChain.doFilter(request, response);
                            } else {
                                log.error("Error setting user authentication in security context", e);
                                handlerExceptionResolver.resolveException(request, response, null, e);
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    log.debug("Token is invalid", e);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
            log.info("Authentication successful for '{}'", request.getRequestURL());
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("Authentication failed for '{}'", request.getRequestURL(), exception);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
