package com.starwars.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalizacaoAtualizacaoDTO {
    
    @NotNull(message = "A localização é obrigatória")
    @Valid
    private LocalizacaoDTO localizacao;
} 