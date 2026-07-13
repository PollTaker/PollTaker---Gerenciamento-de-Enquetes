package com.lucaschalita.polltaker.infrastructure.entities;

import java.time.LocalDateTime;

import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Enquete")
@Entity

public class Enquete {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String titulo;
	
	@Enumerated(EnumType.STRING)
	private StatusEnquete status;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime dataEncerramento;
	
	@ManyToOne
	@JoinColumn(name = "id_criador")
	private Usuario criador;
}
