package com.lucaschalita.polltaker.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String mensagem;
}