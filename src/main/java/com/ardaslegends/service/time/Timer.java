package com.ardaslegends.service.time;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.concurrent.Future;

/**
 * Represents a timer with a future result and a finish time.
 * <p>
 * This class encapsulates a {@link Future} object and the time at which the timer finishes.
 * </p>
 *
 * @param <T> The type of the result returned by the future.
 */
public record Timer<T>(Future<T> future, OffsetDateTime finishesAt) {

    /**
     * Calculates the time left until the timer finishes.
     * <p>
     * This method returns the duration between the current time and the finish time of the timer.
     * </p>
     *
     * @return The duration left until the timer finishes.
     */
    public Duration timeLeft() {
        return Duration.between(OffsetDateTime.now(), finishesAt);
    }
}