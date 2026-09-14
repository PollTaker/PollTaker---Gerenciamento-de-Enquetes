package com.lucaschalita.polltaker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
}
