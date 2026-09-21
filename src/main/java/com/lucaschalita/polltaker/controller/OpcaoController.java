package com.lucaschalita.polltaker.controller;

import com.lucaschalita.polltaker.dto.OpcaoRequestDTO;
import com.lucaschalita.polltaker.dto.OpcaoResponseDTO;
import com.lucaschalita.polltaker.services.OpcaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/opcoes")
@RequiredArgsConstructor
@Tag(name = "Opções")
public class OpcaoController {

    private final OpcaoService opcaoService;

    @PostMapping
    @Operation(summary = "Cria uma opção")
    public ResponseEntity<OpcaoResponseDTO> criar(@Valid @RequestBody OpcaoRequestDTO dto) {
        return ResponseEntity.status(201).body(opcaoService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista opções")
    public ResponseEntity<List<OpcaoResponseDTO>> listar() {
        return ResponseEntity.ok(opcaoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca opção por ID")
    public ResponseEntity<OpcaoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(opcaoService.buscarPorId(id));
    }

    @GetMapping("/enquete/{enqueteId}")
    @Operation(summary = "Lista opções de uma enquete")
    public ResponseEntity<List<OpcaoResponseDTO>> listarPorEnquete(@PathVariable Long enqueteId) {
        return ResponseEntity.ok(opcaoService.listarPorEnquete(enqueteId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza opção")
    public ResponseEntity<OpcaoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody OpcaoRequestDTO dto
    ) {
        return ResponseEntity.ok(opcaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove opção")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        opcaoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
