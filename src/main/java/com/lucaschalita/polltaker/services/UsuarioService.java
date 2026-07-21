package com.lucaschalita.polltaker.services;

import org.springframework.stereotype.Service;
import com.lucaschalita.polltaker.exceptions.*;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository repository;
	
	public UsuarioService (UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public void salvarUsuario (Usuario usuario) {
		repository.saveAndFlush(usuario);
	}
	
	public Usuario buscarPorEmail (String email) {
		return repository.findByEmail(email).orElseThrow(
				() -> new UsuarioNotFoundException("E-mail não encontrado.")
		);
	}

	public void deleteById(Long id) {
		Usuario usuario = repository.findById(id)
				.orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado."));
		repository.delete(usuario);
	}

	public void deleteByEmail (String email) {
		repository.deleteByEmail(email);
	}
	
	public void atualizarUsuarioPorId (Long id, Usuario usuario) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new UsuarioNotFoundException("Usuário não encontrado.")
		);
		Usuario usuarioAtualizado = Usuario.builder()
				.email(usuario.getEmail() != null ? usuario.getEmail() : usuarioEntity.getEmail())
				.nome(usuario.getNome() != null ? usuario.getNome() : usuarioEntity.getNome())
				.id(usuarioEntity.getId())
				.senha(usuarioEntity.getSenha())
				.enquetesCriadas(usuarioEntity.getEnquetesCriadas())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
	}
}
