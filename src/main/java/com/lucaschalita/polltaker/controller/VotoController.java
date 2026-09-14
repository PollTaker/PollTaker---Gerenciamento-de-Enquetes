package com.lucaschalita.polltaker.controller;

import com.lucaschalita.polltaker.dto.VotoDTO;
import com.lucaschalita.polltaker.dto.VotoResponseDTO;
import com.lucaschalita.polltaker.services.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/votos")
@RequiredArgsConstructor
@Tag(name = "Votos")
public class VotoController {

    private final VotoService votoService;

    @PostMapping
    @Operation(summary = "Registra um voto")
    public ResponseEntity<VotoResponseDTO> votar(@Valid @RequestBody VotoDTO dto) {
        return ResponseEntity.status(201).body(votoService.votar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista votos")
    public ResponseEntity<List<VotoResponseDTO>> listar() {
        return ResponseEntity.ok(votoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca voto por ID")
    public ResponseEntity<VotoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(votoService.buscarPorId(id));
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Lista votos de um usuário")
    public ResponseEntity<List<VotoResponseDTO>> buscarPorUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(votoService.buscarPorUsuario(id));
    }

    @GetMapping("/opcao/{id}")
    @Operation(summary = "Lista votos de uma opção")
    public ResponseEntity<List<VotoResponseDTO>> buscarPorOpcao(@PathVariable Long id) {
        return ResponseEntity.ok(votoService.buscarPorOpcao(id));
    }

    @GetMapping("/enquete/{id}")
    @Operation(summary = "Lista votos de uma enquete")
    public ResponseEntity<List<VotoResponseDTO>> buscarPorEnquete(@PathVariable Long id) {
        return ResponseEntity.ok(votoService.buscarPorEnquete(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove voto")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        votoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
