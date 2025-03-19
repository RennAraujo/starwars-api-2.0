package com.starwars.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RebeldeDTO {
    
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    
    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade deve ser maior ou igual a zero")
    private Integer idade;
    
    @NotBlank(message = "O gênero é obrigatório")
    private String genero;
    
    @NotNull(message = "A localização é obrigatória")
    @Valid
    private LocalizacaoDTO localizacao;
    
    @NotEmpty(message = "O inventário não pode estar vazio")
    @Valid
    private List<ItemDTO> inventario;
} 