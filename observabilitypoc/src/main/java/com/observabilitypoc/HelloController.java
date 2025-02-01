package com.observabilitypoc;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    final Counter meuCounter;

    public HelloController(MeterRegistry meterRegistry) {
        this.meuCounter = meterRegistry.counter("observabilitypoc.meuCounter");
    }

    @GetMapping("/rolldice")
    public String index() {
        meuCounter.increment();
        return "Hello world";
    }

}
