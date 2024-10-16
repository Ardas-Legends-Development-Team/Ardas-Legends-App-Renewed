package com.ardaslegends.service.time;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Service for handling time freeze operations.
 * <p>
 * This service provides methods to freeze and unfreeze time, as well as to start a 24-hour timer.
 * </p>
 */
@Slf4j
@Service
public class TimeFreezeService implements Sleep {

    private final ExecutorService virtualExecutorService;
    private boolean isTimeFrozen;

    /**
     * Constructs a {@code TimeFreezeService}.
     *
     * @param virtualExecutorService The executor service used for running timers.
     */
    public TimeFreezeService(ExecutorService virtualExecutorService) {
        isTimeFrozen = false;
        this.virtualExecutorService = virtualExecutorService;
    }

    /**
     * Starts a 24-hour timer and executes the given callback upon completion.
     * <p>
     * This method submits a task to the executor service that sleeps for 24 hours and then calls the provided callback.
     * </p>
     *
     * @param <T>      The type of the result returned by the callback.
     * @param callback The callback to execute after the timer completes.
     * @return A {@link Timer} object containing the future result and the end time of the timer.
     */
    public <T> Timer<T> start24hTimer(Callable<T> callback) {
        log.debug("Call of start24hTimer, Thread before timer: [{}]", Thread.currentThread());
        val now = OffsetDateTime.now();
        val result = virtualExecutorService.submit(() -> {
            log.info("Starting new 24h timer on thread [{}]", Thread.currentThread());
            try {
                sleep(Duration.between(now, now.plusHours(24)));
                log.info("Timer is over - calling callback [{}]", callback.toString());
                return callback.call();
            } catch (InterruptedException e) {
                log.warn("Thread [{}] got interrupted during 24h timer", Thread.currentThread());
                return null;
            }
        });

        return new Timer<>(result, now.plusHours(24));
    }

    /**
     * Freezes time.
     * <p>
     * This method sets the {@code isTimeFrozen} flag to true and logs the current time.
     * </p>
     */
    public void freezeTime() {
        log.info("Freezing time at [{}]", OffsetDateTime.now());
        isTimeFrozen = true;
    }

    /**
     * Unfreezes time.
     * <p>
     * This method sets the {@code isTimeFrozen} flag to false and logs the current time.
     * </p>
     */
    public void unfreezeTime() {
        log.info("Unfreezing time at [{}]", OffsetDateTime.now());
        isTimeFrozen = false;
    }

    /**
     * Checks if time is frozen.
     *
     * @return {@code true} if time is frozen, {@code false} otherwise.
     */
    public boolean isTimeFrozen() {
        return isTimeFrozen;
    }
}