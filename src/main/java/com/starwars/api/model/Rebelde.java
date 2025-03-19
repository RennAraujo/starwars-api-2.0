package com.starwars.api.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rebelde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade deve ser maior ou igual a zero")
    private Integer idade;

    @NotBlank(message = "O gênero é obrigatório")
    private String genero;

    @Embedded
    @Valid
    private Localizacao localizacao;

    @OneToMany(mappedBy = "rebelde", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> inventario = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "denuncias",
            joinColumns = @JoinColumn(name = "denunciado_id"),
            inverseJoinColumns = @JoinColumn(name = "denunciante_id")
    )
    private List<Rebelde> denunciantes = new ArrayList<>();

    private boolean traidor = false;

    public void adicionarDenuncia(Rebelde denunciante) {
        this.denunciantes.add(denunciante);
        if (this.denunciantes.size() >= 3) {
            this.traidor = true;
        }
    }

    public boolean isTraidor() {
        return traidor;
    }

    public void adicionarItem(Item item) {
        item.setRebelde(this);
        this.inventario.add(item);
    }
} 