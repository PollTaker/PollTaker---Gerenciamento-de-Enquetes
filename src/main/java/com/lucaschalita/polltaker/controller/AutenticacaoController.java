package com.lucaschalita.polltaker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lucaschalita.polltaker.dto.CadastroDTO;
import com.lucaschalita.polltaker.dto.LoginDTO;
import com.lucaschalita.polltaker.services.AutenticacaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {
	
	private final AutenticacaoService authService;
	
	@PostMapping("/register")
	public ResponseEntity<Void> register (@RequestBody CadastroDTO dto) {
		authService.cadastrar(dto);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login (@RequestBody LoginDTO dto) {
		boolean autenticado = authService.login(dto);
		
		if(!autenticado) {
			return ResponseEntity.badRequest().body("Senha incorreta.");
		}
		
		return ResponseEntity.ok("Login realizado.");
	}
}
