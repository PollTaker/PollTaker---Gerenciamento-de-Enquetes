package com.lucaschalita.polltaker.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VotoResponseDTO {
    private Long id;
    private Long usuarioId;
    private Long enqueteId;
    private Long opcaoId;
    private LocalDateTime createdAt;
}
