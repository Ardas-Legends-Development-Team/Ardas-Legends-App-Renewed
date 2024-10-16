package com.ardaslegends.configuration.security;

import com.ardaslegends.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for the security settings of the application.
 * This class defines the security filter chain for the application.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final StockpilePluginFilter stockpilePluginFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityProperties securityProperties;

    /**
     * Constructs a new SecurityConfiguration.
     *
     * @param stockpilePluginFilter   the custom filter for stockpile plugin
     * @param jwtAuthenticationFilter the filter for processing JWT authentication
     * @param authenticationProvider  the provider for authentication
     * @param securityProperties      the properties for security configuration
     */
    public SecurityConfiguration(
            StockpilePluginFilter stockpilePluginFilter,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider,
            SecurityProperties securityProperties
    ) {
        this.authenticationProvider = authenticationProvider;
        this.stockpilePluginFilter = stockpilePluginFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.securityProperties = securityProperties;
    }

    /**
     * Configures the security filter chain.
     * This method sets up the security filters, including disabling CSRF, configuring public and role-based routes,
     * and adding custom filters for JWT authentication and stockpile plugin.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    // Public routes
                    securityProperties.getRoles().get("public").forEach(route -> {
                        // Split the route into HTTP method and path
                        String[] parts = route.split(":");
                        auth.requestMatchers(HttpMethod.valueOf(parts[0]), parts[1]).permitAll();
                    });

                    // Dynamic role-based routes
                    for (Role role : Role.values()) {
                        String roleName = role.name().substring(5).toLowerCase();
                        if (securityProperties.getRoles().containsKey(roleName)) {
                            securityProperties.getRoles().get(roleName).forEach(route -> {
                                // Split the route into HTTP method and path
                                String[] parts = route.split(":");
                                // Require the role to access the route with the specified HTTP method and path
                                auth.requestMatchers(HttpMethod.valueOf(parts[0]), parts[1]).hasAuthority(role.name());
                            });
                        }
                    }

                    // Any request must be authenticated
                    auth.anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(stockpilePluginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
