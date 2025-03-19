package com.starwars.api.model;

import com.starwars.api.enums.TipoItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O tipo do item é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoItem tipo;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade mínima é 1")
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "rebelde_id")
    private Rebelde rebelde;

    public Integer getPontos() {
        return tipo.getPontos() * quantidade;
    }
} 