package com.lucaschalita.polltaker.services;

import com.lucaschalita.polltaker.dto.VotoDTO;
import com.lucaschalita.polltaker.dto.VotoResponseDTO;
import com.lucaschalita.polltaker.exceptions.EnqueteEncerradaException;
import com.lucaschalita.polltaker.exceptions.EnqueteNotFoundException;
import com.lucaschalita.polltaker.exceptions.OpcaoNotFoundException;
import com.lucaschalita.polltaker.exceptions.UsuarioJaVotouException;
import com.lucaschalita.polltaker.exceptions.UsuarioNotFoundException;
import com.lucaschalita.polltaker.infrastructure.entities.Voto;
import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;
import com.lucaschalita.polltaker.infrastructure.repositories.EnqueteRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.OpcaoRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.VotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final OpcaoRepository opcaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnqueteRepository enqueteRepository;

    public VotoService(
            VotoRepository votoRepository,
            OpcaoRepository opcaoRepository,
            UsuarioRepository usuarioRepository,
            EnqueteRepository enqueteRepository
    ) {
        this.votoRepository = votoRepository;
        this.opcaoRepository = opcaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.enqueteRepository = enqueteRepository;
    }

    @Transactional
    public VotoResponseDTO votar(VotoDTO dto) {
        var usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado."));

        var enquete = enqueteRepository.findById(dto.getIdEnquete())
                .orElseThrow(() -> new EnqueteNotFoundException("Enquete não encontrada."));

        var opcao = opcaoRepository.findById(dto.getIdOpcao())
                .orElseThrow(() -> new OpcaoNotFoundException("Opção não encontrada."));

        if (!opcao.getEnquete().getId().equals(enquete.getId())) {
            throw new OpcaoNotFoundException("A opção não pertence a esta enquete.");
        }

        if (votoRepository.existsByUsuarioAndEnquete(usuario, enquete)) {
            throw new UsuarioJaVotouException("Usuário já votou nesta enquete.");
        }

        if (enquete.getStatus() != StatusEnquete.ABERTA
                || LocalDateTime.now().isAfter(enquete.getDataEncerramento())) {
            throw new EnqueteEncerradaException("A enquete está encerrada ou não está aberta.");
        }

        Voto voto = Voto.builder()
                .usuario(usuario)
                .enquete(enquete)
                .opcao(opcao)
                .createdAt(LocalDateTime.now())
                .build();

        return toResponse(votoRepository.save(voto));
    }

    @Transactional(readOnly = true)
    public List<VotoResponseDTO> listar() {
        return votoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public VotoResponseDTO buscarPorId(Long id) {
        Voto voto = votoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voto não encontrado."));
        return toResponse(voto);
    }

    @Transactional(readOnly = true)
    public List<VotoResponseDTO> buscarPorUsuario(Long idUsuario) {
        var usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado."));
        return votoRepository.findByUsuario(usuario).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<VotoResponseDTO> buscarPorOpcao(Long idOpcao) {
        var opcao = opcaoRepository.findById(idOpcao)
                .orElseThrow(() -> new OpcaoNotFoundException("Opção não encontrada."));
        return votoRepository.findByOpcao(opcao).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<VotoResponseDTO> buscarPorEnquete(Long idEnquete) {
        var enquete = enqueteRepository.findById(idEnquete)
                .orElseThrow(() -> new EnqueteNotFoundException("Enquete não encontrada."));
        return votoRepository.findByEnquete(enquete).stream().map(this::toResponse).toList();
    }

    @Transactional
    public void remover(Long id) {
        if (!votoRepository.existsById(id)) {
            throw new RuntimeException("Voto não encontrado.");
        }
        votoRepository.deleteById(id);
    }

    private VotoResponseDTO toResponse(Voto v) {
        return VotoResponseDTO.builder()
                .id(v.getId())
                .usuarioId(v.getUsuario().getId())
                .enqueteId(v.getEnquete().getId())
                .opcaoId(v.getOpcao().getId())
                .createdAt(v.getCreatedAt())
                .build();
    }
}
