package com.ardaslegends.service.time;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.concurrent.*;

@Slf4j
@Service
public class TimeFreezeService implements Sleep {

    private boolean isTimeFrozen;

    private final ExecutorService virtualExecutorService;

    public TimeFreezeService(ExecutorService virtualExecutorService) {
        isTimeFrozen = false;
        this.virtualExecutorService = virtualExecutorService;
    }

    public <T> ActiveTimer<T> start24hTimer(Callable<T> callback) {
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
                throw new RuntimeException(e);
            }
        });

        return new ActiveTimer<T>(result, now.plusHours(24));
    }

    public void freezeTime() {
        isTimeFrozen = true;
    }

    public boolean isTimeFrozen() {
        return isTimeFrozen;
    }
}
