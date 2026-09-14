package com.lucaschalita.polltaker.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "criador")
    @Builder.Default
    private List<Enquete> enquetesCriadas = new ArrayList<>();

    @Column(nullable = false)
    private String senha;
}
