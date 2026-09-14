package com.lucaschalita.polltaker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Dados necessários para registrar um voto")
public class VotoDTO {

    @NotNull(message = "O usuário é obrigatório.")
    @Positive(message = "O ID do usuário deve ser positivo.")
    @Schema(example = "1")
    private Long idUsuario;

    @NotNull(message = "A enquete é obrigatória.")
    @Positive(message = "O ID da enquete deve ser positivo.")
    @Schema(example = "1")
    private Long idEnquete;

    @NotNull(message = "A opção é obrigatória.")
    @Positive(message = "O ID da opção deve ser positivo.")
    @Schema(example = "1")
    private Long idOpcao;
}
