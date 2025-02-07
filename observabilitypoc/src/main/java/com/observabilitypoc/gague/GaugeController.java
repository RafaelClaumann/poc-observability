package com.observabilitypoc.gague;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/pedidos")
public class GaugeController {

    private final AtomicInteger pedidosEmProcessamento = new AtomicInteger(0);

    public GaugeController(MeterRegistry meterRegistry) {
        Gauge.builder("pedidos_em_processamento", pedidosEmProcessamento, AtomicInteger::get)
                .description("Número de pedidos em processamento")
                .register(meterRegistry);
    }

    @PostMapping("/incrementar")
    public ResponseEntity<String> incrementarPedido() {
        int gaugeAtual = pedidosEmProcessamento.incrementAndGet();
        return ResponseEntity.ok(String.format("Pedido adicionado! GaugeAtual: %d", gaugeAtual));
    }

    @PostMapping("/decrementar")
    public ResponseEntity<String> decrementarPedido() {
        int gaugeAtual = pedidosEmProcessamento.decrementAndGet();
        return ResponseEntity.ok(String.format("Pedido adicionado! GaugeAtual: %d", gaugeAtual));
    }

}
