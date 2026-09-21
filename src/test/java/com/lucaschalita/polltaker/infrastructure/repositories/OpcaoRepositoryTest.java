package com.lucaschalita.polltaker.infrastructure.repositories;

import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OpcaoRepositoryTest {

    @Autowired
    private OpcaoRepository opcaoRepository;

    @Autowired
    private EnqueteRepository enqueteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar e recuperar uma opção de voto com sucesso")
    public void deveSalvarEBuscarOpcao() {
        Usuario criador = new Usuario();
        criador.setNome("Criador Opcao");
        criador.setEmail("opcao.criador@polltaker.com");
        criador.setSenha("123456");
        criador = usuarioRepository.save(criador);

        Enquete enquete = new Enquete();
        enquete.setTitulo("Linguagem de programação");
        enquete.setDataEncerramento(LocalDateTime.now().plusDays(3));
        enquete.setStatus(StatusEnquete.ABERTA);
        enquete.setCreatedAt(LocalDateTime.now()); //
        enquete.setCriador(criador);
        enquete = enqueteRepository.save(enquete);

        Opcao opcao = new Opcao();
        opcao.setTitulo("Java & Spring Boot");
        opcao.setEnquete(enquete);

        Opcao opcaoSalva = opcaoRepository.save(opcao);

        assertThat(opcaoSalva).isNotNull();
        assertThat(opcaoSalva.getId()).isNotNull();
        assertThat(opcaoSalva.getTitulo()).isEqualTo("Java & Spring Boot");
    }
}