package com.lucaschalita.polltaker.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.lucaschalita.polltaker.infrastructure.entities.Voto;
import com.lucaschalita.polltaker.infrastructure.entities.Opcao;
import com.lucaschalita.polltaker.infrastructure.entities.Usuario;
import com.lucaschalita.polltaker.infrastructure.entities.Enquete;
import com.lucaschalita.polltaker.infrastructure.repositories.VotoRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.OpcaoRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.UsuarioRepository;
import com.lucaschalita.polltaker.infrastructure.repositories.EnqueteRepository;

@Service
public class VotoService {
	private final VotoRepository votoRepository;
	private final OpcaoRepository opcaoRepository;
	private final UsuarioRepository usuarioRepository;
	private final EnqueteRepository enqueteRepository;
	
	public VotoService(VotoRepository votoRepository, OpcaoRepository opcaoRepository, UsuarioRepository usuarioRepository, EnqueteRepository enqueteRepository) {
		this.votoRepository = votoRepository;
		this.opcaoRepository = opcaoRepository;
		this.usuarioRepository = usuarioRepository;
		this.enqueteRepository = enqueteRepository;
	}
	
	public void salvarVoto(Voto voto) {
		votoRepository.saveAndFlush(voto);
	}
	
	public long contarVotosPorOpcao (Long idOpcao) {
		Opcao opcao = opcaoRepository.findById(idOpcao).orElseThrow(
				() -> new RuntimeException("Opção não encontrada.")
		);
		return votoRepository.countByOpcao(opcao);
	}
	
	public long contarVotosPorUsuario (Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
				() -> new RuntimeException("Usuário não encontrado.")
		);
		return votoRepository.countByUsuario(usuario);
	}
	
	public void validarVotoUsuario(Usuario usuario, Enquete enquete) {
		boolean jaVotou = votoRepository.existsByUsuarioAndEnquete(usuario, enquete);
		if (jaVotou) {
			throw new RuntimeException("Este usuário já teve seu voto validado.");
		}
	}
	
	public List<Voto> buscarVotosPorUsuario(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
				() -> new RuntimeException("Usuário não encontrado.")
		);
		
		return votoRepository.findByUsuario(usuario);
	}
	
	public List<Voto> buscarVotosPorOpcao(Long idOpcao) {
		Opcao opcao = opcaoRepository.findById(idOpcao).orElseThrow(
				() -> new RuntimeException("Opção não encontrada.")
		);
		
		return votoRepository.findByOpcao(opcao);
	}
	
	public Optional<Voto> buscarVotoPorUsuarioOpcao(Long idUsuario, Long idOpcao) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();
		Opcao opcao = opcaoRepository.findById(idOpcao).orElseThrow();
		return votoRepository.findByUsuarioAndOpcao(usuario, opcao);
	}
	
	public void votar(Long idUsuario, Long idEnquete, Long idOpcao) {
		Usuario usuario = usuarioRepository.findById(idUsuario)
	            .orElseThrow(() ->
	                    new RuntimeException("Usuário não encontrado."));

	    Enquete enquete = enqueteRepository.findById(idEnquete)
	            .orElseThrow(() ->
	                    new RuntimeException("Enquete não encontrada."));

	    Opcao opcao = opcaoRepository.findById(idOpcao)
	            .orElseThrow(() ->
	                    new RuntimeException("Opção não encontrada."));
	    
	    if (votoRepository.existsByUsuarioAndEnquete(
	            usuario,
	            enquete)) {

	        throw new RuntimeException(
	                "Usuário já votou nesta enquete.");
	    }

	    Voto voto = Voto.builder()
	            .usuario(usuario)
	            .enquete(enquete)
	            .opcao(opcao)
	            .createdAt(LocalDateTime.now())
	            .build();

	    votoRepository.saveAndFlush(voto);
	}
}
