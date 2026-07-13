package com.lucaschalita.polltaker.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.entities.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long>{
	
	long countByOpcao(Opcao opcao);
	
	long countByUsuario(Usuario usuario);
	
	boolean existsByUsuarioAndEnquete(Usuario usuario, Enquete enquete);
	
	List<Voto> findByUsuario(Usuario usuario);
	
	List<Voto> findByOpcao(Opcao opcao);
	
	Optional<Voto> findByUsuarioAndOpcao(Usuario usuario, Opcao opcao);
	
	
}
