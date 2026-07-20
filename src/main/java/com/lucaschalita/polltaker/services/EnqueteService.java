package com.lucaschalita.polltaker.services;

import com.lucaschalita.polltaker.infrastructure.repositories.EnqueteRepository;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.lucaschalita.polltaker.infrastructure.entities.Enquete;

@Service
public class EnqueteService {
	private final EnqueteRepository repository;
	
	public EnqueteService (EnqueteRepository repository) {
		this.repository = repository;
	}
	
	public void salvarEnquete (Enquete enquete) {
		enquete.setCreatedAt(LocalDateTime.now());;
		repository.saveAndFlush(enquete);
	}

	public Enquete buscarPorId(Long id) {

		return repository.findById(id)
				.orElseThrow(
						() -> new RuntimeException("Enquete não encontrada.")
				);
	}

	public List<Enquete> findByDataCriacao(LocalDate data) {
		LocalDateTime inicio = data.atStartOfDay();
		LocalDateTime fim = data.atTime(LocalTime.MAX);
		List<Enquete> enquetes = repository.findByCreatedAtBetween(inicio, fim);
		if (enquetes.isEmpty()) {
			throw new RuntimeException("Não há enquetes criadas neste período.");
		}

		return repository.findByCreatedAtBetween(inicio, fim);
	}
	
	public List<Enquete> findByCriador (Long criadorId) {
		List<Enquete> enquetes = repository.findByCriadorId(criadorId);
		if (enquetes.isEmpty()) {
			throw new RuntimeException("Não há enquetes criadas por este usuário.");
		}
		return repository.findByCriadorId(criadorId);
	}
	
	public void deleteById (Long id) {
		repository.deleteById(id);
	}
	
	public void atualizarEnquetePorId (Long id, Enquete enquete) {
		Enquete enqueteEntity = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Enquete não encontrada.")
		);
		Enquete enqueteAtualizada = Enquete.builder()
				.id(enqueteEntity.getId())
				.titulo(enquete.getTitulo() != null ? enquete.getTitulo() : enqueteEntity.getTitulo())
				.criador(enquete.getCriador() != null ? enquete.getCriador() : enqueteEntity.getCriador())
				.dataEncerramento(enquete.getDataEncerramento() != null ? enquete.getDataEncerramento() : enqueteEntity.getDataEncerramento())
				.createdAt(enqueteEntity.getCreatedAt())
				.status(enquete.getStatus() != null ? enquete.getStatus() : enqueteEntity.getStatus())
				.build();
		repository.saveAndFlush(enqueteAtualizada);
	}
}
