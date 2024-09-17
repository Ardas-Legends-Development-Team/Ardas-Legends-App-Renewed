package com.ardaslegends.configuration;

import com.ardaslegends.service.authentication.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
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
            log.info("No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            log.info("JWT: {}", jwt);
            final String userDiscordId = jwtUtil.extractDiscordIdFromJWT(jwt);
            log.info("User Discord ID: {}", userDiscordId);

            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            //if (userDiscordId != null && authentication == null) {
            if (userDiscordId != null) {
                //UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDiscordId);
                try {
                    boolean isTokenValid = jwtUtil.isTokenValid(jwt);
                    log.info("Token is valid: {}", isTokenValid);
                    //if (jwtUtil.isTokenValid(jwt)) {
                    // UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    //       userDetails,
                    //     null,
                    //   userDetails.getAuthorities()
                    //);

                    //authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //SecurityContextHolder.getContext().setAuthentication(authToken);
                    //}
                } catch (Exception e) {
                    log.error("Token is invalid", e);
                    throw new Exception("Token is invalid");
                }
            }
            log.info("Authentication successful for '{}'", request.getRequestURL());
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("Authentication failed for '{}'", request.getRequestURL(), exception);
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
