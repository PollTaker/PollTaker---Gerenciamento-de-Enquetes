package com.lucaschalita.polltaker.controller;

import com.lucaschalita.polltaker.dto.CadastroDTO;
import com.lucaschalita.polltaker.dto.LoginDTO;
import com.lucaschalita.polltaker.dto.UsuarioResponseDTO;
import com.lucaschalita.polltaker.services.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação")
public class AutenticacaoController {

    private final AutenticacaoService authService;

    @PostMapping("/register")
    @Operation(summary = "Cadastra um usuário")
    public ResponseEntity<UsuarioResponseDTO> register(@Valid @RequestBody CadastroDTO dto) {
        return ResponseEntity.status(201).body(authService.cadastrar(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza login")
    public ResponseEntity<UsuarioResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
