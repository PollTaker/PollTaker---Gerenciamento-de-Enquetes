package com.lucaschalita.polltaker.controller;

import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthWebController {

    private final UsuarioRepository usuarioRepository;

    public AuthWebController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/app/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "success", required = false) String success,
                            Model model) {
        if (error != null) {
            model.addAttribute("mensagemErro", "E-mail ou senha incorretos.");
        }
        if (success != null) {
            model.addAttribute("mensagemSucesso", "Credenciais criadas com sucesso! Faça o login.");
        }
        return "auth/login";
    }

    // ADICIONADO: Método para processar o envio do login via POST
    @PostMapping("/app/login")
    public String processarLogin(@RequestParam("username") String email,
                                 @RequestParam("password") String senha,
                                 HttpSession session) {
        // Busca o usuário pelo e-mail e valida a senha
        var usuarioOpt = usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            // Salva o usuário na sessão do navegador
            session.setAttribute("usuarioLogado", usuarioOpt.get());
            return "redirect:/app/enquetes";
        }

        // Se falhar, redireciona de volta para o login com parâmetro de erro
        return "redirect:/app/login?error";
    }

    @GetMapping("/app/register")
    public String registerPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/register";
    }

    @PostMapping("/app/register")
    public String registerUser(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/app/login?success";
    }
}