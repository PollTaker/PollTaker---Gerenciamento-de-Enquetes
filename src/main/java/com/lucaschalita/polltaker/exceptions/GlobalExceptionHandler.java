package com.lucaschalita.polltaker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ProblemDetail;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ProblemDetail> usuarioNaoEncontrado(UsuarioNotFoundException ex) {
        return problem(HttpStatus.NOT_FOUND, "Usuário não encontrado", ex.getMessage());
    }

    @ExceptionHandler(EnqueteNotFoundException.class)
    public ResponseEntity<ProblemDetail> enqueteNaoEncontrada(EnqueteNotFoundException ex) {
        return problem(HttpStatus.NOT_FOUND, "Enquete não encontrada", ex.getMessage());
    }

    @ExceptionHandler(OpcaoNotFoundException.class)
    public ResponseEntity<ProblemDetail> opcaoNaoEncontrada(OpcaoNotFoundException ex) {
        return problem(HttpStatus.NOT_FOUND, "Opção não encontrada", ex.getMessage());
    }

    @ExceptionHandler(UsuarioJaVotouException.class)
    public ResponseEntity<ProblemDetail> usuarioJaVotou(UsuarioJaVotouException ex) {
        return problem(HttpStatus.CONFLICT, "Voto duplicado", ex.getMessage());
    }

    @ExceptionHandler(EnqueteEncerradaException.class)
    public ResponseEntity<ProblemDetail> enqueteEncerrada(EnqueteEncerradaException ex) {
        return problem(HttpStatus.BAD_REQUEST, "Enquete encerrada", ex.getMessage());
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ResponseEntity<ProblemDetail> entidadeDuplicada(EntidadeDuplicadaException ex) {
        return problem(HttpStatus.CONFLICT, "Registro duplicado", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> argumentosInvalidos(MethodArgumentNotValidException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Um ou mais campos possuem valores inválidos."
        );
        detail.setTitle("Erro de validação");

        List<Map<String, String>> invalidParams = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "name", error.getField(),
                        "reason", error.getDefaultMessage()
                ))
                .toList();

        detail.setProperty("invalid_params", invalidParams);
        return ResponseEntity.badRequest().body(detail);
    }

    private ResponseEntity<ProblemDetail> problem(
            HttpStatus status,
            String title,
            String detailMessage
    ) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, detailMessage);
        detail.setTitle(title);
        return ResponseEntity.status(status).body(detail);
    }
}
