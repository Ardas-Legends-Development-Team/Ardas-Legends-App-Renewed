package com.ardaslegends.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityProperties securityProperties;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider,
            SecurityProperties securityProperties
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.securityProperties = securityProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    // Public routes
                    securityProperties.getRoles().get("public").forEach(route -> auth.requestMatchers(route).permitAll());
                    // User routes
                    securityProperties.getRoles().get("user").forEach(route -> auth.requestMatchers(route).hasRole("USER"));
                    // Staff routes
                    securityProperties.getRoles().get("staff").forEach(route -> auth.requestMatchers(route).hasRole("STAFF"));
                    // Admin routes
                    securityProperties.getRoles().get("admin").forEach(route -> auth.requestMatchers(route).hasRole("ADMIN"));
                    // Commander routes
                    securityProperties.getRoles().get("commander").forEach(route -> auth.requestMatchers(route).hasRole("COMMANDER"));
                    // Lord routes
                    securityProperties.getRoles().get("lord").forEach(route -> auth.requestMatchers(route).hasRole("LORD"));
                    // Faction Leader routes
                    securityProperties.getRoles().get("faction_leader").forEach(route -> auth.requestMatchers(route).hasRole("FACTION_LEADER"));
                    // Any request must be authenticated
                    auth.anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
