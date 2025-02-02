package com.observabilitypoc.gague;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class GaugeController {
    private final GaugeService gaugeService;

    public GaugeController(GaugeService metricService) {
        this.gaugeService = metricService;
    }

    @PostMapping("/incrementar")
    public ResponseEntity<String> incrementarPedido() {
        int gaugeAtual = gaugeService.incrementarPedido();
        return ResponseEntity.ok(String.format("Pedido adicionado! GaugeAtual: %d", gaugeAtual));
    }

    @PostMapping("/decrementar")
    public ResponseEntity<String> decrementarPedido() {
        int gaugeAtual = gaugeService.decrementarPedido();
        return ResponseEntity.ok(String.format("Pedido adicionado! GaugeAtual: %d", gaugeAtual));
    }

}
