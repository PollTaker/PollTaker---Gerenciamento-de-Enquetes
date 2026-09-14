package com.lucaschalita.polltaker.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
    name = "Voto",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_voto_usuario_enquete",
        columnNames = {"id_usuario", "id_enquete"}
    )
)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_opcao", nullable = false)
    private Opcao opcao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_enquete", nullable = false)
    private Enquete enquete;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
