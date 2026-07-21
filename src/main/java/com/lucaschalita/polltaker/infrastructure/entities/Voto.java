package com.lucaschalita.polltaker.infrastructure.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Voto")
@Entity

public class Voto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_opcao")
	private Opcao opcao;
	
	@ManyToOne
	@JoinColumn(name = "id_enquete")
	private Enquete enquete;
	
	private LocalDateTime createdAt;
}
