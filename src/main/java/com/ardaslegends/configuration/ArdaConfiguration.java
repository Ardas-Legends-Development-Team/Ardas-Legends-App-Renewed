package com.ardaslegends.configuration;

import com.ardaslegends.repository.player.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Configuration class for the Arda Legends application.
 * This class defines various beans and configuration settings for the application.
 */
@RequiredArgsConstructor
@Slf4j
@Configuration
public class ArdaConfiguration {
    private final PlayerRepository playerRepository;

    /**
     * Creates a virtual thread per task executor service.
     *
     * @return the configured ExecutorService
     */
    @Bean
    public ExecutorService virtualExecutorService() {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        log.info("Initialized virtualExecutorService");
        return executor;
    }

    /**
     * Creates a URL validator bean.
     *
     * @return the configured UrlValidator
     */
    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator();
    }

    /**
     * Creates a RestTemplate bean.
     *
     * @return the configured RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Creates a UserDetailsService bean that loads user-specific data.
     *
     * @return the configured UserDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        return discordId -> playerRepository.findByDiscordID(discordId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Creates an AuthenticationManager bean.
     *
     * @param config the AuthenticationConfiguration to use
     * @return the configured AuthenticationManager
     * @throws Exception if an error occurs while creating the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates an AuthenticationProvider bean.
     *
     * @return the configured AuthenticationProvider
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }
}