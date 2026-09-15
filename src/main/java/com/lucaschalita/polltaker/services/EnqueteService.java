package com.lucaschalita.polltaker.services;

import com.lucaschalita.polltaker.dto.EnqueteRequestDTO;
import com.lucaschalita.polltaker.dto.EnqueteResponseDTO;
import com.lucaschalita.polltaker.dto.OpcaoResponseDTO;
import com.lucaschalita.polltaker.exceptions.EnqueteNotFoundException;
import com.lucaschalita.polltaker.exceptions.UsuarioNotFoundException;
import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;
import com.lucaschalita.polltaker.infrastructure.repositories.EnqueteRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.OpcaoRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class EnqueteService {

    private final EnqueteRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final OpcaoRepository opcaoRepository;

    public EnqueteService(EnqueteRepository repository, UsuarioRepository usuarioRepository, OpcaoRepository opcaoRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.opcaoRepository = opcaoRepository;
    }

    @Transactional
    public EnqueteResponseDTO salvar(EnqueteRequestDTO dto) {
        var criador = usuarioRepository.findById(dto.getCriadorId())
                .orElseThrow(() -> new UsuarioNotFoundException("Criador não encontrado."));

        Enquete enquete = Enquete.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .criador(criador)
                .status(dto.getStatus() == null ? StatusEnquete.ABERTA : dto.getStatus())
                .createdAt(LocalDateTime.now())
                .dataEncerramento(dto.getDataEncerramento())
                .build();

        // Salva a enquete primeiro para gerar o ID
        Enquete enqueteSalva = repository.save(enquete);

        // Salva as opções enviadas no formulário
        if (dto.getOpcoes() != null) {
            for (String tituloOpcao : dto.getOpcoes()) {
                if (tituloOpcao != null && !tituloOpcao.trim().isEmpty()) {
                    Opcao opcao = new Opcao();
                    opcao.setTitulo(tituloOpcao);
                    opcao.setEnquete(enqueteSalva);
                    opcaoRepository.save(opcao);
                }
            }
        }

        return toResponse(enqueteSalva);
    }

    // (Mantenha os demais métodos inalterados...)

    @Transactional(readOnly = true)
    public List<EnqueteResponseDTO> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Enquete buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EnqueteNotFoundException("Enquete não encontrada."));
    }

    @Transactional(readOnly = true)
    public EnqueteResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<EnqueteResponseDTO> findByDataCriacao(LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);

        return repository.findByCreatedAtBetween(inicio, fim)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EnqueteResponseDTO> findByCriador(Long criadorId) {
        return repository.findByCriadorId(criadorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public EnqueteResponseDTO atualizar(Long id, EnqueteRequestDTO dto) {
        Enquete enquete = buscarEntidade(id);

        var criador = usuarioRepository.findById(dto.getCriadorId())
                .orElseThrow(() -> new UsuarioNotFoundException("Criador não encontrado."));

        enquete.setTitulo(dto.getTitulo());
        enquete.setDescricao(dto.getDescricao());
        enquete.setCriador(criador);
        enquete.setDataEncerramento(dto.getDataEncerramento());

        if (dto.getStatus() != null) {
            enquete.setStatus(dto.getStatus());
        }

        return toResponse(repository.save(enquete));
    }

    @Transactional
    public void remover(Long id) {
        Enquete enquete = buscarEntidade(id);
        repository.delete(enquete);
    }

    private EnqueteResponseDTO toResponse(Enquete e) {
        List<OpcaoResponseDTO> opcoes = e.getOpcoes() == null
                ? List.of()
                : e.getOpcoes().stream()
                .map(o -> OpcaoResponseDTO.builder()
                        .id(o.getId())
                        .titulo(o.getTitulo())
                        .enqueteId(e.getId())
                        .build())
                .toList();

        return EnqueteResponseDTO.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .descricao(e.getDescricao())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .dataEncerramento(e.getDataEncerramento())
                .criadorId(e.getCriador().getId())
                .criadorNome(e.getCriador().getNome())
                .opcoes(opcoes)
                .build();
    }
}