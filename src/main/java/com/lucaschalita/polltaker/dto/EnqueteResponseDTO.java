package com.lucaschalita.polltaker.dto;

import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EnqueteResponseDTO {
    private Long id;
    private String titulo;
    private StatusEnquete status;
    private LocalDateTime createdAt;
    private LocalDateTime dataEncerramento;
    private Long criadorId;
    private String criadorNome;
    private List<OpcaoResponseDTO> opcoes;
}
