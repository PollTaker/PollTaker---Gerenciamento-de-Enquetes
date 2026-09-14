package com.lucaschalita.polltaker.infrastructure.repositories;

import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.entities.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    long countByOpcao(Opcao opcao);
    long countByUsuario(Usuario usuario);
    boolean existsByUsuarioAndEnquete(Usuario usuario, Enquete enquete);
    List<Voto> findByUsuario(Usuario usuario);
    List<Voto> findByOpcao(Opcao opcao);
    List<Voto> findByEnquete(Enquete enquete);
    Optional<Voto> findByUsuarioAndOpcao(Usuario usuario, Opcao opcao);
}
