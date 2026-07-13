package com.lucaschalita.polltaker.infrastructure.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Usuario")
@Entity

public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "nome")
	private String nome;
	
	@OneToMany(mappedBy = "criador")
	private List<Enquete> enquetesCriadas;
	
	private String senha;
}
