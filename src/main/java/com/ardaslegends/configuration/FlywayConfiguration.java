package com.ardaslegends.configuration;

import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Flyway.
 * This class defines a bean for Flyway auto-configuration.
 */
@Configuration
public class FlywayConfiguration {

    /**
     * Creates a FlywayAutoConfiguration bean.
     *
     * @return the configured FlywayAutoConfiguration
     */
    @Bean
    public FlywayAutoConfiguration autoConfiguration() {
        return new FlywayAutoConfiguration();
    }

}
