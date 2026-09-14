package com.lucaschalita.polltaker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpcaoResponseDTO {
    private Long id;
    private String titulo;
    private Long enqueteId;
}
