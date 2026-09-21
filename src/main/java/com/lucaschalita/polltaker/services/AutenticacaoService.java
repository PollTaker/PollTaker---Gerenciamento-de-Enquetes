package com.lucaschalita.polltaker.services;

import com.lucaschalita.polltaker.dto.CadastroDTO;
import com.lucaschalita.polltaker.dto.LoginDTO;
import com.lucaschalita.polltaker.dto.UsuarioResponseDTO;
import com.lucaschalita.polltaker.exceptions.EntidadeDuplicadaException;
import com.lucaschalita.polltaker.exceptions.UsuarioNotFoundException;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public AutenticacaoService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public UsuarioResponseDTO cadastrar(CadastroDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new EntidadeDuplicadaException("E-mail já cadastrado.");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(encoder.encode(dto.getSenha()))
                .build();

        usuario = repository.save(usuario);
        return toResponse(usuario);
    }

    public UsuarioResponseDTO login(LoginDTO dto) {
        Usuario usuario = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsuarioNotFoundException("E-mail não encontrado."));

        if (!encoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return toResponse(usuario);
    }

    private UsuarioResponseDTO toResponse(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }
}
