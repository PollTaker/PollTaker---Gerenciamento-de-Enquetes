package com.lucaschalita.polltaker.controller;

import com.lucaschalita.polltaker.dto.EnqueteRequestDTO;
import com.lucaschalita.polltaker.dto.EnqueteResponseDTO;
import com.lucaschalita.polltaker.services.EnqueteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enquetes")
@RequiredArgsConstructor
@Tag(name = "Enquetes")
public class EnqueteController {

    private final EnqueteService enqueteService;

    @PostMapping
    @Operation(summary = "Cria uma enquete")
    public ResponseEntity<EnqueteResponseDTO> criar(@Valid @RequestBody EnqueteRequestDTO dto) {
        return ResponseEntity.status(201).body(enqueteService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista enquetes")
    public ResponseEntity<List<EnqueteResponseDTO>> listar() {
        return ResponseEntity.ok(enqueteService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca enquete por ID")
    public ResponseEntity<EnqueteResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(enqueteService.buscarPorId(id));
    }

    @GetMapping("/data")
    @Operation(summary = "Lista enquetes criadas em uma data")
    public ResponseEntity<List<EnqueteResponseDTO>> buscarPorData(@RequestParam LocalDate dataCriacao) {
        return ResponseEntity.ok(enqueteService.findByDataCriacao(dataCriacao));
    }

    @GetMapping("/criador/{id}")
    @Operation(summary = "Lista enquetes de um criador")
    public ResponseEntity<List<EnqueteResponseDTO>> buscarPorCriador(@PathVariable Long id) {
        return ResponseEntity.ok(enqueteService.findByCriador(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza enquete")
    public ResponseEntity<EnqueteResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EnqueteRequestDTO dto
    ) {
        return ResponseEntity.ok(enqueteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove enquete")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        enqueteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
