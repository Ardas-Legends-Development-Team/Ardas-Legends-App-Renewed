package com.ardaslegends.service.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

/**
 * Configuration class for scheduling and asynchronous operations.
 * <p>
 * This class enables scheduling and asynchronous processing in the Spring application context.
 * It also provides a bean for the system clock.
 * </p>
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleConfig {

    /**
     * Provides a system clock bean.
     * <p>
     * This method returns a {@link Clock} instance that represents the system default time zone.
     * </p>
     *
     * @return A {@link Clock} instance representing the system default time zone.
     */
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}