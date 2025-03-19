package com.starwars.api.controller;

import com.starwars.api.dto.DenunciaDTO;
import com.starwars.api.dto.LocalizacaoAtualizacaoDTO;
import com.starwars.api.dto.NegociacaoDTO;
import com.starwars.api.dto.RebeldeDTO;
import com.starwars.api.model.Rebelde;
import com.starwars.api.service.RebeldeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rebeldes")
@RequiredArgsConstructor
@Tag(name = "Rebeldes", description = "Operações para gerenciamento de rebeldes")
public class RebeldeController {

    private final RebeldeService rebeldeService;
    
    @GetMapping
    @Operation(summary = "Listar todos os rebeldes", description = "Recupera uma lista de todos os rebeldes cadastrados")
    public ResponseEntity<List<Rebelde>> listarTodos() {
        return ResponseEntity.ok(rebeldeService.listarTodos());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar rebelde por ID", description = "Recupera um rebelde pelo seu ID")
    public ResponseEntity<Rebelde> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rebeldeService.buscarPorId(id));
    }
    
    @PostMapping
    @Operation(summary = "Cadastrar rebelde", description = "Cadastra um novo rebelde com seu inventário inicial")
    public ResponseEntity<Rebelde> cadastrar(@Valid @RequestBody RebeldeDTO rebeldeDTO) {
        Rebelde rebelde = rebeldeService.cadastrar(rebeldeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rebelde);
    }
    
    @PutMapping("/{id}/localizacao")
    @Operation(summary = "Atualizar localização", description = "Atualiza a localização de um rebelde pelo seu ID")
    public ResponseEntity<Rebelde> atualizarLocalizacao(
            @PathVariable Long id,
            @Valid @RequestBody LocalizacaoAtualizacaoDTO localizacaoDTO) {
        return ResponseEntity.ok(rebeldeService.atualizarLocalizacao(id, localizacaoDTO));
    }
    
    @PostMapping("/denunciar")
    @Operation(summary = "Denunciar rebelde", description = "Registra uma denúncia contra um rebelde")
    public ResponseEntity<Void> denunciar(@Valid @RequestBody DenunciaDTO denunciaDTO) {
        rebeldeService.denunciar(denunciaDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PostMapping("/negociar")
    @Operation(summary = "Negociar itens", description = "Realiza a negociação de itens entre dois rebeldes")
    public ResponseEntity<Void> negociarItens(@Valid @RequestBody NegociacaoDTO negociacaoDTO) {
        rebeldeService.negociarItens(negociacaoDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
} 