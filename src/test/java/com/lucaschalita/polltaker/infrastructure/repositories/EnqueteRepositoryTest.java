package com.lucaschalita.polltaker.infrastructure.repositories;

import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EnqueteRepositoryTest {

    @Autowired
    private EnqueteRepository enqueteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar e recuperar uma enquete com sucesso")
    public void deveSalvarEBuscarEnquete() {
        Usuario criador = new Usuario();
        criador.setNome("Admin Enquete");
        criador.setEmail("admin.enquete@polltaker.com");
        criador.setSenha("123456");
        criador = usuarioRepository.save(criador);

        Enquete enquete = new Enquete();
        enquete.setTitulo("Qual sua stack favorita?");
        enquete.setDataEncerramento(LocalDateTime.now().plusDays(7));
        enquete.setStatus(StatusEnquete.ABERTA);
        enquete.setCreatedAt(LocalDateTime.now());
        enquete.setCriador(criador);

        Enquete enqueteSalva = enqueteRepository.save(enquete);

        assertThat(enqueteSalva).isNotNull();
        assertThat(enqueteSalva.getId()).isNotNull();
        assertThat(enqueteSalva.getTitulo()).isEqualTo("Qual sua stack favorita?");
    }
}