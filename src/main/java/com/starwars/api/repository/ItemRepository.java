package com.starwars.api.repository;

import com.starwars.api.enums.TipoItem;
import com.starwars.api.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    @Query("SELECT i FROM Item i WHERE i.rebelde.id = :rebeldeId AND i.tipo = :tipo")
    List<Item> findByRebeldeIdAndTipo(@Param("rebeldeId") Long rebeldeId, @Param("tipo") TipoItem tipo);
    
    @Query("SELECT AVG(i.quantidade) FROM Item i JOIN i.rebelde r WHERE r.traidor = false AND i.tipo = :tipo")
    Double findAverageQuantityByTipo(@Param("tipo") TipoItem tipo);
    
    @Query("SELECT SUM(i.quantidade * :pontos) FROM Item i JOIN i.rebelde r WHERE r.traidor = true AND i.tipo = :tipo")
    Integer findTotalPointsLostByTipo(@Param("tipo") TipoItem tipo, @Param("pontos") int pontos);
} 