package com.lucaschalita.polltaker.services;

import org.springframework.stereotype.Service;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.repositories.OpcaoRepository;

@Service
public class OpcaoService {
	private final OpcaoRepository repository;
	
	public OpcaoService (OpcaoRepository repository) {
		this.repository = repository;
	}
	
	public void salvarOpcao (Opcao opcao) {
		repository.saveAndFlush(opcao);
	}
	
	public Opcao buscarPorTitulo (String titulo) {
		return repository.findByTitulo(titulo).orElseThrow(
				() -> new RuntimeException("Opção não encontrada.")
		);
	}
	
	public void deletarPorId (Long id) {
		repository.deleteById(id);
	}
	
	public void atualizarOpcaoPorId (Long id, Opcao opcao) {
		Opcao opcaoEntity = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Opção não encontrada.")
		);
		Opcao opcaoAtualizada = Opcao.builder()
				.id(opcaoEntity.getId())
				.titulo(opcao.getTitulo() != null ? opcao.getTitulo() : opcaoEntity.getTitulo())
				.enquete(opcao.getEnquete() != null ? opcao.getEnquete() : opcaoEntity.getEnquete())
				.build();
		repository.saveAndFlush(opcaoAtualizada);
	}
}
