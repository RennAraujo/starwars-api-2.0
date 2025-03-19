package com.starwars.api.service;

import com.starwars.api.enums.TipoItem;
import com.starwars.api.repository.ItemRepository;
import com.starwars.api.repository.RebeldeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final RebeldeRepository rebeldeRepository;
    private final ItemRepository itemRepository;
    
    public double calcularPorcentagemTraidores() {
        long totalRebeldes = rebeldeRepository.count();
        if (totalRebeldes == 0) {
            return 0.0;
        }
        
        long traidores = rebeldeRepository.countByTraidorTrue();
        return (double) traidores / totalRebeldes * 100.0;
    }
    
    public double calcularPorcentagemRebeldes() {
        long totalRebeldes = rebeldeRepository.count();
        if (totalRebeldes == 0) {
            return 0.0;
        }
        
        long rebeldesFieis = rebeldeRepository.countByTraidorFalse();
        return (double) rebeldesFieis / totalRebeldes * 100.0;
    }
    
    public Map<TipoItem, Double> calcularMediaRecursosPorRebelde() {
        Map<TipoItem, Double> mediaRecursos = new HashMap<>();
        
        for (TipoItem tipo : TipoItem.values()) {
            Double media = itemRepository.findAverageQuantityByTipo(tipo);
            mediaRecursos.put(tipo, media != null ? media : 0.0);
        }
        
        return mediaRecursos;
    }
    
    public int calcularPontosPerdidos() {
        int pontosPerdidos = 0;
        
        for (TipoItem tipo : TipoItem.values()) {
            Integer pontosPerdidosPorTipo = itemRepository.findTotalPointsLostByTipo(tipo, tipo.getPontos());
            pontosPerdidos += (pontosPerdidosPorTipo != null ? pontosPerdidosPorTipo : 0);
        }
        
        return pontosPerdidos;
    }
} 