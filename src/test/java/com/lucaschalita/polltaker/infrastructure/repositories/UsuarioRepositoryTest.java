package com.lucaschalita.polltaker.repository;

import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar e recuperar um usuário com sucesso no H2")
    public void deveSalvarEBuscarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setNome("Lucas Chalita");
        usuario.setEmail("lucas.teste@polltaker.com");
        usuario.setSenha("senhaSegura123");


        Usuario usuarioSalvo = usuarioRepository.save(usuario);


        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getId()).isNotNull();
        assertThat(usuarioSalvo.getEmail()).isEqualTo("lucas.teste@polltaker.com");
    }

    @Test
    @DisplayName("Deve lançar exceção de integridade ao tentar salvar usuário sem senha (NotNull)")
    public void deveFalharAoSalvarSemSenha() {

        Usuario usuario = new Usuario();
        usuario.setNome("Usuário Inválido");
        usuario.setEmail("invalido@polltaker.com");
        usuario.setSenha(null);


        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.saveAndFlush(usuario);
        });
    }
}