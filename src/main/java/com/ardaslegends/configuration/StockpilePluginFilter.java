package com.ardaslegends.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class StockpilePluginFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    public StockpilePluginFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String secretHeader = securityProperties.getPluginAccess().getHeaderName();
        String secretValue = securityProperties.getPluginAccess().getSecret();
        // Get the secret from the request header if it exists
        // If not then the request is not from the plugin and continue with the security checks
        try {
            String secret = request.getHeader(secretHeader);
            if (secret == null) {
                throw new Exception();
            }
            if (secretValue.equals(secret)) {
                logger.info("Request is from the plugin");
                // Bypass security checks
                SecurityContextHolder.getContext().setAuthentication(
                        new StockpilePluginAuthenticationToken(new WebAuthenticationDetailsSource().buildDetails(request))
                );
            }
        } catch (Exception e) {
            logger.info("Request is not from the plugin");
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}