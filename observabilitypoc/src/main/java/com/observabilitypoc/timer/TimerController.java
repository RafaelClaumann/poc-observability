package com.observabilitypoc.timer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/timer")
public class TimerController {

    private final Random random = new Random();
    private final Timer requestTimer;

    public TimerController(MeterRegistry meterRegistry) {
        this.requestTimer = Timer.builder("request_latency_seconds")
                .description("Tempo de resposta das requisições")
                .tag("service", "TimerService")
                .register(meterRegistry);
    }

    @GetMapping("/latency")
    public String simulateLatency() {
        // Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and the
        // specified value (exclusive), drawn from this random number generator's sequence.
        final int latency = random.nextInt(1000);
        requestTimer.record(() -> {
            try {
                Thread.sleep(latency); // Simula um atraso de <latency>ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        return String.format("Latencia registrada! %d ms", latency);
    }
}
