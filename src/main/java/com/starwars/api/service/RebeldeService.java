package com.starwars.api.service;

import com.starwars.api.dto.*;
import com.starwars.api.enums.TipoItem;
import com.starwars.api.exception.NegocioException;
import com.starwars.api.exception.RecursoNaoEncontradoException;
import com.starwars.api.model.Item;
import com.starwars.api.model.Localizacao;
import com.starwars.api.model.Rebelde;
import com.starwars.api.repository.ItemRepository;
import com.starwars.api.repository.RebeldeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RebeldeService {

    private final RebeldeRepository rebeldeRepository;
    private final ItemRepository itemRepository;
    
    public List<Rebelde> listarTodos() {
        return rebeldeRepository.findAll();
    }
    
    public Rebelde buscarPorId(Long id) {
        return rebeldeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Rebelde não encontrado com ID: " + id));
    }
    
    @Transactional
    public Rebelde cadastrar(RebeldeDTO rebeldeDTO) {
        Rebelde rebelde = new Rebelde();
        rebelde.setNome(rebeldeDTO.getNome());
        rebelde.setIdade(rebeldeDTO.getIdade());
        rebelde.setGenero(rebeldeDTO.getGenero());
        
        Localizacao localizacao = new Localizacao(
                rebeldeDTO.getLocalizacao().getLatitude(),
                rebeldeDTO.getLocalizacao().getLongitude(),
                rebeldeDTO.getLocalizacao().getNomeBase()
        );
        rebelde.setLocalizacao(localizacao);
        
        Rebelde rebeldeSalvo = rebeldeRepository.save(rebelde);
        
        for (ItemDTO itemDTO : rebeldeDTO.getInventario()) {
            Item item = new Item();
            item.setTipo(itemDTO.getTipo());
            item.setQuantidade(itemDTO.getQuantidade());
            item.setRebelde(rebeldeSalvo);
            rebeldeSalvo.getInventario().add(item);
        }
        
        return rebeldeRepository.save(rebeldeSalvo);
    }
    
    @Transactional
    public Rebelde atualizarLocalizacao(Long id, LocalizacaoAtualizacaoDTO localizacaoDTO) {
        Rebelde rebelde = buscarPorId(id);
        
        if (rebelde.isTraidor()) {
            throw new NegocioException("Não é possível atualizar a localização de um traidor");
        }
        
        Localizacao novaLocalizacao = new Localizacao(
                localizacaoDTO.getLocalizacao().getLatitude(),
                localizacaoDTO.getLocalizacao().getLongitude(),
                localizacaoDTO.getLocalizacao().getNomeBase()
        );
        
        rebelde.setLocalizacao(novaLocalizacao);
        return rebeldeRepository.save(rebelde);
    }
    
    @Transactional
    public void denunciar(DenunciaDTO denunciaDTO) {
        if (denunciaDTO.getDenuncianteId().equals(denunciaDTO.getDenunciadoId())) {
            throw new NegocioException("Um rebelde não pode denunciar a si mesmo");
        }
        
        Rebelde denunciante = buscarPorId(denunciaDTO.getDenuncianteId());
        Rebelde denunciado = buscarPorId(denunciaDTO.getDenunciadoId());
        
        if (denunciado.getDenunciantes().contains(denunciante)) {
            throw new NegocioException("Este rebelde já denunciou o alvo anteriormente");
        }
        
        denunciado.adicionarDenuncia(denunciante);
        rebeldeRepository.save(denunciado);
    }
    
    @Transactional
    public void negociarItens(NegociacaoDTO negociacaoDTO) {
        Rebelde rebeldeOfertante = buscarPorId(negociacaoDTO.getRebeldeOfertanteId());
        Rebelde rebeldeReceptor = buscarPorId(negociacaoDTO.getRebeldeReceptorId());
        
        validarNegociacao(rebeldeOfertante, rebeldeReceptor);
        
        int pontosOfertados = calcularPontos(negociacaoDTO.getItensOfertados());
        int pontosDesejados = calcularPontos(negociacaoDTO.getItensDesejados());
        
        if (pontosOfertados != pontosDesejados) {
            throw new NegocioException(
                    "Negociação inválida. Pontos ofertados (" + pontosOfertados + 
                    ") diferentes dos pontos desejados (" + pontosDesejados + ")");
        }
        
        validarItensDisponiveis(rebeldeOfertante, negociacaoDTO.getItensOfertados());
        validarItensDisponiveis(rebeldeReceptor, negociacaoDTO.getItensDesejados());
        
        realizarTroca(rebeldeOfertante, rebeldeReceptor, negociacaoDTO.getItensOfertados(), negociacaoDTO.getItensDesejados());
    }
    
    private void validarNegociacao(Rebelde rebeldeOfertante, Rebelde rebeldeReceptor) {
        if (rebeldeOfertante.isTraidor()) {
            throw new NegocioException("O rebelde ofertante é um traidor e não pode negociar");
        }
        
        if (rebeldeReceptor.isTraidor()) {
            throw new NegocioException("O rebelde receptor é um traidor e não pode negociar");
        }
    }
    
    private int calcularPontos(List<ItemDTO> itens) {
        return itens.stream()
                .mapToInt(item -> item.getTipo().getPontos() * item.getQuantidade())
                .sum();
    }
    
    private void validarItensDisponiveis(Rebelde rebelde, List<ItemDTO> itens) {
        Map<TipoItem, Integer> quantidadesNecessarias = new HashMap<>();
        
        for (ItemDTO itemDTO : itens) {
            quantidadesNecessarias.merge(itemDTO.getTipo(), itemDTO.getQuantidade(), Integer::sum);
        }
        
        for (Map.Entry<TipoItem, Integer> entry : quantidadesNecessarias.entrySet()) {
            TipoItem tipo = entry.getKey();
            Integer quantidadeNecessaria = entry.getValue();
            
            Integer quantidadeDisponivel = rebelde.getInventario().stream()
                    .filter(item -> item.getTipo() == tipo)
                    .mapToInt(Item::getQuantidade)
                    .sum();
            
            if (quantidadeDisponivel < quantidadeNecessaria) {
                throw new NegocioException(
                        "O rebelde " + rebelde.getNome() + " não possui " + quantidadeNecessaria + 
                        " unidades de " + tipo + " disponíveis para negociação");
            }
        }
    }
    
    @Transactional
    private void realizarTroca(Rebelde rebeldeOfertante, Rebelde rebeldeReceptor, 
                             List<ItemDTO> itensOfertados, List<ItemDTO> itensDesejados) {
        // Remover itens ofertados do inventário do ofertante
        removerItens(rebeldeOfertante, itensOfertados);
        
        // Remover itens desejados do inventário do receptor
        removerItens(rebeldeReceptor, itensDesejados);
        
        // Adicionar itens ofertados ao inventário do receptor
        adicionarItens(rebeldeReceptor, itensOfertados);
        
        // Adicionar itens desejados ao inventário do ofertante
        adicionarItens(rebeldeOfertante, itensDesejados);
        
        rebeldeRepository.save(rebeldeOfertante);
        rebeldeRepository.save(rebeldeReceptor);
    }
    
    private void removerItens(Rebelde rebelde, List<ItemDTO> itensParaRemover) {
        Map<TipoItem, Integer> quantidadesParaRemover = new HashMap<>();
        
        for (ItemDTO itemDTO : itensParaRemover) {
            quantidadesParaRemover.merge(itemDTO.getTipo(), itemDTO.getQuantidade(), Integer::sum);
        }
        
        for (Map.Entry<TipoItem, Integer> entry : quantidadesParaRemover.entrySet()) {
            TipoItem tipo = entry.getKey();
            Integer quantidadeParaRemover = entry.getValue();
            
            List<Item> itensDoTipo = rebelde.getInventario().stream()
                    .filter(item -> item.getTipo() == tipo)
                    .collect(Collectors.toList());
            
            int quantidadeRestanteParaRemover = quantidadeParaRemover;
            
            for (Item item : itensDoTipo) {
                if (quantidadeRestanteParaRemover <= 0) {
                    break;
                }
                
                if (item.getQuantidade() <= quantidadeRestanteParaRemover) {
                    quantidadeRestanteParaRemover -= item.getQuantidade();
                    rebelde.getInventario().remove(item);
                } else {
                    item.setQuantidade(item.getQuantidade() - quantidadeRestanteParaRemover);
                    quantidadeRestanteParaRemover = 0;
                }
            }
        }
    }
    
    private void adicionarItens(Rebelde rebelde, List<ItemDTO> itensParaAdicionar) {
        Map<TipoItem, Integer> quantidadesParaAdicionar = new HashMap<>();
        
        for (ItemDTO itemDTO : itensParaAdicionar) {
            quantidadesParaAdicionar.merge(itemDTO.getTipo(), itemDTO.getQuantidade(), Integer::sum);
        }
        
        for (Map.Entry<TipoItem, Integer> entry : quantidadesParaAdicionar.entrySet()) {
            TipoItem tipo = entry.getKey();
            Integer quantidade = entry.getValue();
            
            List<Item> itensExistentes = rebelde.getInventario().stream()
                    .filter(item -> item.getTipo() == tipo)
                    .collect(Collectors.toList());
            
            if (!itensExistentes.isEmpty()) {
                itensExistentes.get(0).setQuantidade(itensExistentes.get(0).getQuantidade() + quantidade);
            } else {
                Item novoItem = new Item();
                novoItem.setTipo(tipo);
                novoItem.setQuantidade(quantidade);
                rebelde.adicionarItem(novoItem);
            }
        }
    }
} 