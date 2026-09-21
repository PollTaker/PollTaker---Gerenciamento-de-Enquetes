package com.lucaschalita.polltaker.services;

import com.lucaschalita.polltaker.dto.UsuarioRequestDTO;
import com.lucaschalita.polltaker.dto.UsuarioResponseDTO;
import com.lucaschalita.polltaker.exceptions.EntidadeDuplicadaException;
import com.lucaschalita.polltaker.exceptions.UsuarioNotFoundException;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new EntidadeDuplicadaException("E-mail já cadastrado.");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(encoder.encode(dto.getSenha()))
                .build();

        return toResponse(repository.save(usuario));
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public Usuario buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado."));
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("E-mail não encontrado."));
        return toResponse(usuario);
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarEntidade(id);

        repository.findByEmail(dto.getEmail())
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new EntidadeDuplicadaException("E-mail já cadastrado.");
                });

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(encoder.encode(dto.getSenha()));
        }

        return toResponse(repository.save(usuario));
    }

    public void remover(Long id) {
        Usuario usuario = buscarEntidade(id);
        repository.delete(usuario);
    }

    private UsuarioResponseDTO toResponse(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }
}
