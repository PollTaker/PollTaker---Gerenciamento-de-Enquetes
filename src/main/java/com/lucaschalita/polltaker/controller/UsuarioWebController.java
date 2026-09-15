package com.lucaschalita.polltaker.controller;

import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app/usuarios")
public class UsuarioWebController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioWebController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("usuario", new Usuario());
        return "usuarios/gerenciar";
    }

    @PostMapping
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/app/usuarios";
    }
}