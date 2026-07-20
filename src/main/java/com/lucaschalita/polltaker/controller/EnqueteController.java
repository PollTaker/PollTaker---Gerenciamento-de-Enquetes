package com.lucaschalita.polltaker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.services.EnqueteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/enquetes")
@RequiredArgsConstructor
public class EnqueteController {

	private final EnqueteService enqueteService;

	@PostMapping
	public ResponseEntity<Void> salvarEnquete(
			@RequestBody Enquete enquete) {

		enqueteService.salvarEnquete(enquete);

		return ResponseEntity.status(201).build();
	}

	@GetMapping("/data")
	public ResponseEntity<List<Enquete>> buscarPorData(
			@RequestParam LocalDate dataCriacao) {

		return ResponseEntity.ok(
				enqueteService.findByDataCriacao(dataCriacao)
		);
	}

	@GetMapping("/criador/{id}")
	public ResponseEntity<List<Enquete>> buscarPorCriador(
			@PathVariable Long id) {

		return ResponseEntity.ok(
				enqueteService.findByCriador(id)
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Enquete> buscarPorId(
			@PathVariable Long id) {

		return ResponseEntity.ok(
				enqueteService.buscarPorId(id)
		);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarEnquete(
			@PathVariable Long id) {

		enqueteService.deleteById(id);

		return ResponseEntity.noContent().build();
	}



	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarEnquete(
			@PathVariable Long id,
			@RequestBody Enquete enquete) {


		enqueteService.atualizarEnquetePorId(id, enquete);

		return ResponseEntity.ok().build();
	}
}