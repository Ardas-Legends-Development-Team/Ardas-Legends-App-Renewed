package com.ardaslegends.service.time;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;

/**
 * Interface for handling sleep operations.
 * <p>
 * This interface provides default methods for sleeping for a specified duration or until a specified date.
 * </p>
 */
public interface Sleep {

    /**
     * Sleeps for the specified duration.
     * <p>
     * This method causes the current thread to sleep for the specified duration.
     * </p>
     *
     * @param duration The duration to sleep.
     * @throws RuntimeException if the thread is interrupted while sleeping.
     */
    default void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sleeps until the specified date.
     * <p>
     * This method causes the current thread to sleep until the specified date.
     * </p>
     *
     * @param date The date until which to sleep.
     * @throws RuntimeException if the thread is interrupted while sleeping.
     */
    default void sleepUntil(Temporal date) {
        sleep(Duration.between(OffsetDateTime.now(), date));
    }
}