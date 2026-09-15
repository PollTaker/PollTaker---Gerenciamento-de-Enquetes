package com.lucaschalita.polltaker.dto;

import com.lucaschalita.polltaker.infrastructure.enums.StatusEnquete;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Dados de uma enquete")
public class EnqueteRequestDTO {

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres.")
    @Schema(example = "Qual linguagem você prefere?")
    private String titulo;

    @NotBlank
    @Size(max = 200)
    private List<String> opcoes = new ArrayList<>();

    @NotNull(message = "O criador é obrigatório.")
    @Positive(message = "O ID do criador deve ser positivo.")
    @Schema(example = "1")
    private Long criadorId;

    @NotNull(message = "A data de encerramento é obrigatória.")
    @Future(message = "A data de encerramento deve estar no futuro.")
    @Schema(example = "2026-12-31T23:59:59")
    private LocalDateTime dataEncerramento;

    @Schema(example = "ABERTA")
    private StatusEnquete status;

    @Size(max = 100, message = "Insira uma descrição.")
    @Schema(example = "Insira uma descrição aqui.")
    private String descricao;
}
