package com.lucaschalita.polltaker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Opção de uma enquete")
public class OpcaoRequestDTO {

    @NotBlank(message = "O título da opção é obrigatório.")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres.")
    @Schema(example = "Java")
    private String titulo;

    @NotNull(message = "A enquete é obrigatória.")
    @Positive(message = "O ID da enquete deve ser positivo.")
    @Schema(example = "1")
    private Long enqueteId;
}
