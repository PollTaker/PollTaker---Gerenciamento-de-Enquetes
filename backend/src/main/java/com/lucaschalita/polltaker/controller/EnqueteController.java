package com.lucaschalita.polltaker.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.services.EnqueteService;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enquete")
@RequiredArgsConstructor
public class EnqueteController {
	
	private final EnqueteService enqueteService;
	
	@PostMapping
	public ResponseEntity<Void> salvarEnquete (@RequestBody Enquete enquete) {
		enqueteService.salvarEnquete(enquete);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/data")
	public ResponseEntity<List<Enquete>> buscarEnquetePorDataCriacao (@RequestParam LocalDate dataCriacao) {
		return ResponseEntity.ok(enqueteService.findByDataCriacao(dataCriacao));
	}
	
	@GetMapping("/criador")
	public ResponseEntity<List<Enquete>> buscarEnquetePorCriador (@RequestParam Long criadorId) {
		return ResponseEntity.ok(enqueteService.findByCriador(criadorId));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleterEnquetePorId (@RequestParam Long id) {
		enqueteService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> atualizarEnquetePorId (@RequestParam Long id, @RequestBody Enquete enquete) {
		enqueteService.atualizarEnquetePorId(id, enquete);
		return ResponseEntity.ok().build();
	}
}
