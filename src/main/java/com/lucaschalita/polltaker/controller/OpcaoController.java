package com.lucaschalita.polltaker.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.services.OpcaoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/opcoes")
@RequiredArgsConstructor
public class OpcaoController {


	private final OpcaoService opcaoService;



	@PostMapping
	public ResponseEntity<Void> salvarOpcao(
			@RequestBody Opcao opcao) {

		opcaoService.salvarOpcao(opcao);

		return ResponseEntity.status(201).build();
	}



	@GetMapping
	public ResponseEntity<Opcao> buscarPorTitulo(
			@RequestParam String titulo) {


		return ResponseEntity.ok(
				opcaoService.buscarPorTitulo(titulo)
		);
	}



	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarOpcao(
			@PathVariable Long id) {

		opcaoService.deletarPorId(id);

		return ResponseEntity.noContent().build();
	}



	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarOpcao(
			@PathVariable Long id,
			@RequestBody Opcao opcao) {


		opcaoService.atualizarOpcaoPorId(id, opcao);

		return ResponseEntity.ok().build();
	}
}