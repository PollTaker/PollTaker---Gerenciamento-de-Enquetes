package com.lucaschalita.polltaker.infrastructure.repositories;

import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcaoRepository extends JpaRepository<Opcao, Long> {
    List<Opcao> findByEnqueteId(Long enqueteId);
}
