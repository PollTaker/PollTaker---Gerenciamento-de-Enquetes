package com.lucaschalita.polltaker.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lucaschalita.polltaker.dto.VotoDTO;
import com.lucaschalita.polltaker.infrastructure.entities.Voto;
import com.lucaschalita.polltaker.services.VotoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/votos")
@RequiredArgsConstructor
public class VotoController {
	private final VotoService votoService;
	
	@PostMapping
	public ResponseEntity<String> votar(@RequestBody VotoDTO dto) {
		votoService.votar(dto.getIdUsuario(), dto.getIdEnquete(), dto.getIdOpcao());
		return ResponseEntity.ok("Voto registrado com sucesso.");
	}
	
	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<List<Voto>> buscarVotosPorUsuario(@PathVariable Long idUsuario) {
		return ResponseEntity.ok(votoService.buscarVotosPorUsuario(idUsuario));
	}
	
	@GetMapping("/opcao/{idOpcao}")
	public ResponseEntity<List<Voto>> buscarVotosPorOpcao(@PathVariable Long idOpcao) {
		return ResponseEntity.ok(votoService.buscarVotosPorOpcao(idOpcao));
	}
	
	@GetMapping("/usuario/{idUsuario}/total")
	public ResponseEntity<Long> contarVotosPorUsuario(@PathVariable Long idUsuario) {
		return ResponseEntity.ok(votoService.contarVotosPorUsuario(idUsuario));
	}
	
	@GetMapping("/opcao/{idOpcao}/total")
	public ResponseEntity<Long> contarVotosPorOpcao(@PathVariable Long idOpcao) {
		return ResponseEntity.ok(votoService.contarVotosPorOpcao(idOpcao));
	}
}
