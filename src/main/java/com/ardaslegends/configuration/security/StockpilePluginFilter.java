package com.ardaslegends.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for handling authentication of requests from the Stockpile Plugin.
 * This filter checks for a specific header and secret value to authenticate
 * requests from the Stockpile Plugin and bypasses security checks if valid.
 */
@Component
public class StockpilePluginFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    /**
     * Constructs a new StockpilePluginFilter.
     *
     * @param securityProperties the security properties to use for configuration
     */
    public StockpilePluginFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /**
     * Filters incoming requests to check for the Stockpile Plugin's authentication header.
     * If the header and secret value match, the request is authenticated as coming from the plugin.
     * Otherwise, the request proceeds with the normal security checks.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
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
                logger.debug("Request is from the plugin");
                // Bypass security checks
                SecurityContextHolder.getContext().setAuthentication(
                        new StockpilePluginAuthenticationToken(new WebAuthenticationDetailsSource().buildDetails(request))
                );
            }
        } catch (Exception e) {
            logger.debug("Request is not from the plugin");
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}