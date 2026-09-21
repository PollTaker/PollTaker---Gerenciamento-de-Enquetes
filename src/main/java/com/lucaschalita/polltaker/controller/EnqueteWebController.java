package com.lucaschalita.polltaker.controller;

import com.lucaschalita.polltaker.dto.EnqueteRequestDTO;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.repositories.OpcaoRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.VotoRepository;
import com.lucaschalita.polltaker.services.EnqueteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app/enquetes")
public class EnqueteWebController {

    private final EnqueteService enqueteService;
    private final OpcaoRepository opcaoRepository;
    private final VotoRepository votoRepository;

    public EnqueteWebController(EnqueteService enqueteService, OpcaoRepository opcaoRepository, VotoRepository votoRepository) {
        this.enqueteService = enqueteService;
        this.opcaoRepository = opcaoRepository;
        this.votoRepository = votoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("enquetes", enqueteService.listar());
        return "enquetes/lista";
    }

    @GetMapping("/novo")
    public String formularioCriar(Model model, HttpSession session) {
        // Valida se o usuário está logado na sessão
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/app/login";
        }

        model.addAttribute("enqueteDto", new EnqueteRequestDTO());
        return "enquetes/novo";
    }

    @PostMapping
    public String criar(@ModelAttribute EnqueteRequestDTO enqueteDto, HttpSession session) {
        // Recupera o usuário logado da sessão atual
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/app/login";
        }

        // Define implicitamente o ID do criador com base no usuário logado
        enqueteDto.setCriadorId(usuarioLogado.getId());

        enqueteService.salvar(enqueteDto);
        return "redirect:/app/enquetes";
    }

    // (Mantenha os demais métodos de detalhes e votação inalterados)
    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/app/login";
        }

        var enquete = enqueteService.buscarEntidade(id);
        model.addAttribute("enquete", enquete);
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "enquetes/detalhe";
    }

    @PostMapping("/{id}/opcoes")
    public String adicionarOpcao(@PathVariable Long id, @RequestParam String tituloOpcao) {
        var enquete = enqueteService.buscarEntidade(id);
        Opcao opcao = new Opcao();
        opcao.setTitulo(tituloOpcao);
        opcao.setEnquete(enquete);
        opcaoRepository.save(opcao);
        return "redirect:/app/enquetes/" + id;
    }

    @PostMapping("/{id}/votar")
    public String votar(@PathVariable Long id, @RequestParam Long opcaoId, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/app/login";
        }

        var enquete = enqueteService.buscarEntidade(id);
        var opcao = opcaoRepository.findById(opcaoId).orElseThrow();

        var voto = new com.lucaschalita.polltaker.infrastructure.entities.Voto();
        voto.setEnquete(enquete);
        voto.setUsuario(usuarioLogado); // Usa o usuário logado implicitamente no voto também!
        voto.setOpcao(opcao);
        voto.setCreatedAt(java.time.LocalDateTime.now());

        votoRepository.save(voto);
        return "redirect:/app/enquetes/" + id;
    }
}