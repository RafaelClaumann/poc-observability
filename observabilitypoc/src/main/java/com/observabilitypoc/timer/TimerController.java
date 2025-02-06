package com.observabilitypoc.timer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/timer")
public class TimerController {

    private final Random random = new Random();
    private final TimerService timerService;

    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }

    @GetMapping("/latency")
    public String simulateLatency() {
        // Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and the
        // specified value (exclusive), drawn from this random number generator's sequence.
        final int latency = random.nextInt(1000);
        timerService.recordRequest(() -> {
            try {
                Thread.sleep(latency); // Simula um atraso de <latency>ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        return String.format("Latencia registrada! %d ms", latency);
    }
}
