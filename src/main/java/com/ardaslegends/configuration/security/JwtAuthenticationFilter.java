package com.ardaslegends.configuration.security;

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

/**
 * Filter that processes JWT authentication for incoming HTTP requests.
 * This filter checks for the presence of a JWT token in the Authorization header,
 * validates the token, and sets the authentication in the security context if valid.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Constructs a new JwtAuthenticationFilter.
     *
     * @param jwtUtil                  the utility class for handling JWT operations
     * @param userDetailsService       the service to load user-specific data
     * @param handlerExceptionResolver the resolver for handling exceptions
     */
    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Filters incoming HTTP requests to process JWT authentication.
     * This method checks for the presence of a JWT token in the Authorization header,
     * validates the token, and sets the authentication in the security context if valid.
     * If the token is invalid, the response status is set to SC_UNAUTHORIZED.
     * If an error occurs during the filtering process, the exception is resolved by the handler exception resolver.
     * If the user does not exist and the request is to the /register endpoint, a new user is created.
     * Otherwise, an error is logged and the response status is set to SC_UNAUTHORIZED.
     * If the token is valid, the authentication is set in the security context and the filter chain is continued.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during the filtering process
     * @throws IOException      if an I/O error occurs during the filtering process
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("Processing authentication for '{}'", request.getRequestURL());
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
                try {
                    boolean isTokenValid = jwtUtil.isTokenValid(jwt);
                    log.debug("Token is valid: {}", isTokenValid);
                    if (jwtUtil.isTokenValid(jwt)) {
                        try {
                            log.debug("Loading user by username to get UserDetails");
                            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDiscordId);
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        } catch (Exception e) {
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
            log.debug("Authentication successful for '{}'", request.getRequestURL());
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("Authentication failed for '{}'", request.getRequestURL(), exception);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
