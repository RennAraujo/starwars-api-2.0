package com.starwars.api.enums;

import lombok.Getter;

@Getter
public enum TipoItem {
    ARMA(4),
    MUNICAO(3),
    AGUA(2),
    COMIDA(1);

    private final int pontos;

    TipoItem(int pontos) {
        this.pontos = pontos;
    }
} 