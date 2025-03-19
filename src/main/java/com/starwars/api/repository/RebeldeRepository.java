package com.starwars.api.repository;

import com.starwars.api.model.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RebeldeRepository extends JpaRepository<Rebelde, Long> {
    
    @Query("SELECT COUNT(r) FROM Rebelde r WHERE r.traidor = true")
    long countByTraidorTrue();
    
    @Query("SELECT COUNT(r) FROM Rebelde r WHERE r.traidor = false")
    long countByTraidorFalse();
} 