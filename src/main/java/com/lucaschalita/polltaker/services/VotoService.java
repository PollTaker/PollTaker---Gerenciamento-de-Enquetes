package com.lucaschalita.polltaker.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;
import org.springframework.stereotype.Service;
import com.lucaschalita.polltaker.exceptions.*;
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
				() -> new OpcaoNotFoundException("Opção não encontrada.")
		);
		return votoRepository.countByOpcao(opcao);
	}
	
	public long contarVotosPorUsuario (Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
				() -> new UsuarioNotFoundException("Usuário não encontrado.")
		);
		return votoRepository.countByUsuario(usuario);
	}
	
	public void validarVotoUsuario(Usuario usuario, Enquete enquete) {
		boolean jaVotou = votoRepository.existsByUsuarioAndEnquete(usuario, enquete);
		if (jaVotou) {
			throw new UsuarioJaVotouException("Este usuário já teve seu voto validado.");
		}
	}
	
	public List<Voto> buscarVotosPorUsuario(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
				() -> new UsuarioNotFoundException("Usuário não encontrado.")
		);
		return votoRepository.findByUsuario(usuario);
	}
	
	public List<Voto> buscarVotosPorOpcao(Long idOpcao) {
		Opcao opcao = opcaoRepository.findById(idOpcao).orElseThrow(
				() -> new OpcaoNotFoundException("Opção não encontrada.")
		);
		return votoRepository.findByOpcao(opcao);
	}
	
	public Optional<Voto> buscarVotoPorUsuarioOpcao(Long idUsuario, Long idOpcao) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();
		Opcao opcao = opcaoRepository.findById(idOpcao).orElseThrow();
		return votoRepository.findByUsuarioAndOpcao(usuario, opcao);
	}

	@Transactional
	public void votar(Long idUsuario, Long idEnquete, Long idOpcao) {
		Usuario usuario = usuarioRepository.findById(idUsuario)
	            .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado."));

	    Enquete enquete = enqueteRepository.findById(idEnquete)
	            .orElseThrow(() -> new EnqueteNotFoundException("Enquete não encontrada."));

	    Opcao opcao = opcaoRepository.findById(idOpcao)
	            .orElseThrow(() -> new OpcaoNotFoundException("Opção não encontrada."));

		if (!opcao.getEnquete().getId().equals(idEnquete)) {
			throw new OpcaoNotFoundException("A opção não pertence a esta enquete.");
		}

	    if (votoRepository.existsByUsuarioAndEnquete(usuario, enquete)) {
	        throw new UsuarioJaVotouException("Usuário já votou nesta enquete.");
	    }

		if (enquete.getStatus() == StatusEnquete.FECHADA) {
			throw new EnqueteEncerradaException("A enquete está encerrada.");
		}

		if (LocalDateTime.now().isAfter(enquete.getDataEncerramento())) {
			throw new EnqueteEncerradaException("A enquete está encerrada.");
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
