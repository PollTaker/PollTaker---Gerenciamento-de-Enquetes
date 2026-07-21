package com.lucaschalita.polltaker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lucaschalita.polltaker.dto.VotoDTO;
import com.lucaschalita.polltaker.infrastructure.entities.Voto;
import com.lucaschalita.polltaker.services.VotoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/votos")
@RequiredArgsConstructor
public class VotoController {
	private final VotoService votoService;

	@PostMapping
	public ResponseEntity<String> votar(@RequestBody VotoDTO dto) {
		votoService.votar(dto.getIdUsuario(), dto.getIdEnquete(), dto.getIdOpcao());
		return ResponseEntity.ok("Voto registrado com sucesso.");
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<Voto>> buscarPorUsuario(@PathVariable Long id) {
		return ResponseEntity.ok(votoService.buscarVotosPorUsuario(id));
	}

	@GetMapping("/opcao/{id}")
	public ResponseEntity<List<Voto>> buscarPorOpcao(@PathVariable Long id) {
		return ResponseEntity.ok(votoService.buscarVotosPorOpcao(id));
	}
}