package com.observabilitypoc.counter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/counter")
public class CounterController {

    private final Random random = new Random();
    private final MeterRegistry meterRegistry;

    public CounterController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GetMapping
    public ResponseEntity<Map<String, Boolean>> notification() {
        boolean isGeneric = random.nextBoolean();
        boolean wasScheduled = random.nextBoolean();

        Counter.builder("notification_type")
                .tag("isGeneric", isGeneric ? "Genérica" : "Completa")
                .tags("wasScheduled", wasScheduled ? "Agendada" : "Não Agendada")
                .register(meterRegistry)
                .increment();

        return ResponseEntity.ok(
                Map.of(
                        "mensagem_generica", isGeneric,
                        "mensagem_agendada", wasScheduled
                )
        );
    }
}
