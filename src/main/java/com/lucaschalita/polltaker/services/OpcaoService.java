package com.lucaschalita.polltaker.services;

import com.lucaschalita.polltaker.dto.OpcaoRequestDTO;
import com.lucaschalita.polltaker.dto.OpcaoResponseDTO;
import com.lucaschalita.polltaker.exceptions.EnqueteNotFoundException;
import com.lucaschalita.polltaker.exceptions.OpcaoNotFoundException;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.repositories.EnqueteRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.OpcaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OpcaoService {

    private final OpcaoRepository repository;
    private final EnqueteRepository enqueteRepository;

    public OpcaoService(OpcaoRepository repository, EnqueteRepository enqueteRepository) {
        this.repository = repository;
        this.enqueteRepository = enqueteRepository;
    }

    @Transactional
    public OpcaoResponseDTO salvar(OpcaoRequestDTO dto) {
        var enquete = enqueteRepository.findById(dto.getEnqueteId())
                .orElseThrow(() -> new EnqueteNotFoundException("Enquete não encontrada."));

        Opcao opcao = Opcao.builder()
                .titulo(dto.getTitulo())
                .enquete(enquete)
                .build();

        return toResponse(repository.save(opcao));
    }

    @Transactional(readOnly = true)
    public List<OpcaoResponseDTO> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Opcao buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OpcaoNotFoundException("Opção não encontrada."));
    }

    @Transactional(readOnly = true)
    public OpcaoResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<OpcaoResponseDTO> listarPorEnquete(Long enqueteId) {
        return repository.findByEnqueteId(enqueteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public OpcaoResponseDTO atualizar(Long id, OpcaoRequestDTO dto) {
        Opcao opcao = buscarEntidade(id);

        var enquete = enqueteRepository.findById(dto.getEnqueteId())
                .orElseThrow(() -> new EnqueteNotFoundException("Enquete não encontrada."));

        opcao.setTitulo(dto.getTitulo());
        opcao.setEnquete(enquete);

        return toResponse(repository.save(opcao));
    }

    @Transactional
    public void remover(Long id) {
        Opcao opcao = buscarEntidade(id);
        repository.delete(opcao);
    }

    private OpcaoResponseDTO toResponse(Opcao o) {
        return OpcaoResponseDTO.builder()
                .id(o.getId())
                .titulo(o.getTitulo())
                .enqueteId(o.getEnquete().getId())
                .build();
    }
}
