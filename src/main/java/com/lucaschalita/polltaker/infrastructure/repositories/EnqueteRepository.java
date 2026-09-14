package com.lucaschalita.polltaker.infrastructure.repositories;

import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EnqueteRepository extends JpaRepository<Enquete, Long> {
    List<Enquete> findByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Enquete> findByCriadorId(Long criadorId);
}
