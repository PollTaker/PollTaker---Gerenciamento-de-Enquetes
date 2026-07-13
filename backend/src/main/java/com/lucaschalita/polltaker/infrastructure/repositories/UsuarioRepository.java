package com.lucaschalita.polltaker.infrastructure.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import jakarta.transaction.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail (String email);
	
	@Transactional
	void deleteByEmail (String email);
}
