package com.observabilitypoc.timer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

@Service
public class TimerService {
    private final Timer requestTimer;

    public TimerService(MeterRegistry meterRegistry) {
        this.requestTimer = Timer.builder("request_latency_seconds")
                .description("Tempo de resposta das requisições")
                .tag("service", "TimerService")
                .register(meterRegistry);
    }

    public void recordRequest(Runnable task) {
        requestTimer.record(task);
    }
}
