package com.lucaschalita.polltaker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Credenciais de acesso")
public class LoginDTO {

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Schema(example = "lucas@email.com")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Schema(example = "123456")
    private String senha;
}
