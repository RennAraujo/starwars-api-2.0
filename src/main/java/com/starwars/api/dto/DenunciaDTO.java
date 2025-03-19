package com.starwars.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DenunciaDTO {
    
    @NotNull(message = "O ID do denunciante é obrigatório")
    private Long denuncianteId;
    
    @NotNull(message = "O ID do denunciado é obrigatório")
    private Long denunciadoId;
} 