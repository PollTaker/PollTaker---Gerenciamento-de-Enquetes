package com.lucaschalita.polltaker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;


	@PostMapping
	public ResponseEntity<Void> salvarUsuario(
			@RequestBody Usuario usuario) {

		usuarioService.salvarUsuario(usuario);

		return ResponseEntity.status(201).build();
	}


	@GetMapping
	public ResponseEntity<Usuario> buscarUsuarioPorEmail(
			@RequestParam String email) {

		return ResponseEntity.ok(
				usuarioService.buscarPorEmail(email)
		);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteByIdId(
			@PathVariable Long id) {

		usuarioService.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarUsuario(
			@PathVariable Long id,
			@RequestBody Usuario usuario) {

		usuarioService.atualizarUsuarioPorId(id, usuario);

		return ResponseEntity.ok().build();
	}
}