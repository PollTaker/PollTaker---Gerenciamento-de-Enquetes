package com.lucaschalita.polltaker.infrastructure.repositories;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.*;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Data
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository repository;

    @Test
    public void deveSalvarEBuscarUsuarioPorEmail() {
        Usuario usuario = Usuario.builder().nome("Teste").email("teste@email.com").senha("123456").build();
        repository.save(usuario);
        Optional<Usuario> encontrado = repository.findByEmail("teste@email.com");
        assertThat(encontrado).isPresent();
    }
}