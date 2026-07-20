package com.lucaschalita.polltaker.infrastructure.repositories;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import java.util.List;

import jakarta.transaction.Transactional;

public interface EnqueteRepository extends JpaRepository<Enquete, Long>{
	
	List<Enquete> findByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fim);
	
	List<Enquete> findByCriadorId(Long criadorId);
	
	@Transactional
	void deleteById(Long id);
}
