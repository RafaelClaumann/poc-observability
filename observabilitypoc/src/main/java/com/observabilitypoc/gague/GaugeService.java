package com.observabilitypoc.gague;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GaugeService {
    private final AtomicInteger pedidosEmProcessamento = new AtomicInteger(0);

    public GaugeService(MeterRegistry meterRegistry) {
        Gauge.builder("pedidos_em_processamento", pedidosEmProcessamento, AtomicInteger::get)
                .description("Número de pedidos em processamento")
                .register(meterRegistry);
    }

    public int incrementarPedido() {
        return pedidosEmProcessamento.incrementAndGet();
    }

    public int decrementarPedido() {
        return pedidosEmProcessamento.decrementAndGet();
    }

}
