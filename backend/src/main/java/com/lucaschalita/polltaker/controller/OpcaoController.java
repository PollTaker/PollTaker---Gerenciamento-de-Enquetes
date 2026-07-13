package com.lucaschalita.polltaker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.services.OpcaoService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/opcao")
@RequiredArgsConstructor
public class OpcaoController {
	private final OpcaoService opcaoService;
	
	@PostMapping
	public ResponseEntity<Void> salvarOpcao(@RequestBody Opcao opcao) {
		opcaoService.salvarOpcao(opcao);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<Opcao> buscarOpcaoPorTitulo(@RequestParam String titulo) {
		return ResponseEntity.ok(opcaoService.buscarPorTitulo(titulo));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarPorId(@RequestParam Long id) {
		opcaoService.deletarPorId(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> atualizarOpcaoPorId(@RequestParam Long id, @RequestBody Opcao opcao) {
		opcaoService.atualizarOpcaoPorId(id, opcao);
		return ResponseEntity.ok().build();
	}
}
