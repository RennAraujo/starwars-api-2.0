package com.starwars.api.controller;

import com.starwars.api.enums.TipoItem;
import com.starwars.api.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Operações para obtenção de relatórios estatísticos")
public class RelatorioController {

    private final RelatorioService relatorioService;
    
    @GetMapping("/porcentagem-traidores")
    @Operation(summary = "Porcentagem de traidores", description = "Retorna a porcentagem de rebeldes que são traidores")
    public ResponseEntity<Double> getPorcentagemTraidores() {
        return ResponseEntity.ok(relatorioService.calcularPorcentagemTraidores());
    }
    
    @GetMapping("/porcentagem-rebeldes")
    @Operation(summary = "Porcentagem de rebeldes", description = "Retorna a porcentagem de rebeldes que são fiéis")
    public ResponseEntity<Double> getPorcentagemRebeldes() {
        return ResponseEntity.ok(relatorioService.calcularPorcentagemRebeldes());
    }
    
    @GetMapping("/media-recursos")
    @Operation(summary = "Média de recursos", description = "Retorna a média de cada tipo de recurso por rebelde")
    public ResponseEntity<Map<TipoItem, Double>> getMediaRecursos() {
        return ResponseEntity.ok(relatorioService.calcularMediaRecursosPorRebelde());
    }
    
    @GetMapping("/pontos-perdidos")
    @Operation(summary = "Pontos perdidos", description = "Retorna a quantidade de pontos perdidos devido a traidores")
    public ResponseEntity<Integer> getPontosPerdidos() {
        return ResponseEntity.ok(relatorioService.calcularPontosPerdidos());
    }
} 