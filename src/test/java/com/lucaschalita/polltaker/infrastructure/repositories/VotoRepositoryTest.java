package com.lucaschalita.polltaker.infrastructure.repositories;

import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.entities.Voto;
import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class VotoRepositoryTest {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnqueteRepository enqueteRepository;

    @Autowired
    private OpcaoRepository opcaoRepository;

    @Test
    @DisplayName("Deve registrar e recuperar um voto com sucesso")
    public void deveSalvarEBuscarVoto() {
        Usuario usuario = new Usuario();
        usuario.setNome("Eleitor");
        usuario.setEmail("eleitor@polltaker.com");
        usuario.setSenha("123456");
        usuario = usuarioRepository.save(usuario);

        Enquete enquete = new Enquete();
        enquete.setTitulo("Trabalho Remoto vs Presencial");
        enquete.setDataEncerramento(LocalDateTime.now().plusDays(5));
        enquete.setStatus(StatusEnquete.ABERTA);
        enquete.setCreatedAt(LocalDateTime.now());
        enquete.setCriador(usuario);
        enquete = enqueteRepository.save(enquete);

        Opcao opcao = new Opcao();
        opcao.setTitulo("Remoto");
        opcao.setEnquete(enquete);
        opcao = opcaoRepository.save(opcao);

        Voto voto = new Voto();
        voto.setUsuario(usuario);
        voto.setEnquete(enquete);
        voto.setOpcao(opcao);
        voto.setCreatedAt(LocalDateTime.now()); // <-- ADICIONADO AQUI

        Voto votoSalvo = votoRepository.save(voto);

        assertThat(votoSalvo).isNotNull();
        assertThat(votoSalvo.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar votar duas vezes na mesma enquete")
    public void deveFalharVotoDuplicado() {
        Usuario usuario = new Usuario();
        usuario.setNome("Eleitor Duplo");
        usuario.setEmail("duplo.eleitor@polltaker.com");
        usuario.setSenha("123456");
        usuario = usuarioRepository.save(usuario);

        Enquete enquete = new Enquete();
        enquete.setTitulo("Enquete Única");
        enquete.setDataEncerramento(LocalDateTime.now().plusDays(5));
        enquete.setStatus(StatusEnquete.ABERTA);
        enquete.setCreatedAt(LocalDateTime.now());
        enquete.setCriador(usuario);
        enquete = enqueteRepository.save(enquete);

        Opcao opcao1 = new Opcao();
        opcao1.setTitulo("Opção 1");
        opcao1.setEnquete(enquete);
        opcao1 = opcaoRepository.save(opcao1);

        Opcao opcao2 = new Opcao();
        opcao2.setTitulo("Opção 2");
        opcao2.setEnquete(enquete);
        opcao2 = opcaoRepository.save(opcao2);

        Voto voto1 = new Voto();
        voto1.setUsuario(usuario);
        voto1.setEnquete(enquete);
        voto1.setOpcao(opcao1);
        voto1.setCreatedAt(LocalDateTime.now()); // <-- ADICIONADO AQUI
        votoRepository.saveAndFlush(voto1);

        Voto voto2 = new Voto();
        voto2.setUsuario(usuario);
        voto2.setEnquete(enquete);
        voto2.setOpcao(opcao2);
        voto2.setCreatedAt(LocalDateTime.now()); // <-- ADICIONADO AQUI

        assertThrows(DataIntegrityViolationException.class, () -> {
            votoRepository.saveAndFlush(voto2);
        });
    }
}