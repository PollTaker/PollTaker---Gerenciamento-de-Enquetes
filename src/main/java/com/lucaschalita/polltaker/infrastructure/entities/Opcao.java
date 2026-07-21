package com.lucaschalita.polltaker.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "OpcaoVoto")
@Entity

public class Opcao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "titulo")
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name = "id_enquete")
	private Enquete enquete;
}
