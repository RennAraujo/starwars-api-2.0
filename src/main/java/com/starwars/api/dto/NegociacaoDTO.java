package com.starwars.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NegociacaoDTO {
    
    @NotNull(message = "O ID do rebelde ofertante é obrigatório")
    private Long rebeldeOfertanteId;
    
    @NotNull(message = "O ID do rebelde receptor é obrigatório")
    private Long rebeldeReceptorId;
    
    @NotEmpty(message = "A lista de itens ofertados não pode estar vazia")
    @Valid
    private List<ItemDTO> itensOfertados;
    
    @NotEmpty(message = "A lista de itens desejados não pode estar vazia")
    @Valid
    private List<ItemDTO> itensDesejados;
} 