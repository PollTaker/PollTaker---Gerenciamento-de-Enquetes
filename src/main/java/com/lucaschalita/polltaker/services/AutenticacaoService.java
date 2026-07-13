package com.lucaschalita.polltaker.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lucaschalita.polltaker.dto.CadastroDTO;
import com.lucaschalita.polltaker.dto.LoginDTO;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;

@Service
public class AutenticacaoService {
	
	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	
	public AutenticacaoService (UsuarioRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
	public void cadastrar (CadastroDTO dto) {
		if (repository.findByEmail(dto.getEmail()).isPresent()) {
			throw new RuntimeException("E-mail já cadastrado");
		}
		
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(encoder.encode(dto.getSenha()))
				.build();
		
		repository.saveAndFlush(usuario);
	}
	
	public boolean login (LoginDTO dto) {
		Usuario usuario = repository.findByEmail(dto.getEmail()).orElseThrow(
				() -> new RuntimeException("E-mail não encontrado.")
		);
		
		return encoder.matches(dto.getSenha(), usuario.getSenha());
	}
		
}
