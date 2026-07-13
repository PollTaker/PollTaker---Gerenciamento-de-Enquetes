package com.lucaschalita.polltaker.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import jakarta.transaction.Transactional;

public interface OpcaoRepository extends JpaRepository<Opcao, Long>{
	
	Optional<Opcao> findByTitulo(String titulo);
	
	@Transactional
	void deleteById (Long id);
}
