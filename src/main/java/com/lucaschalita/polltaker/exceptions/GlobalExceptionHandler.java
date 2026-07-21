package com.lucaschalita.polltaker.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.lucaschalita.polltaker.exceptions.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorResponse> usuarioNaoEncontrado(UsuarioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now()))
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(EnqueteNotFoundException.class)
    public ResponseEntity<ErrorResponse> enqueteNaoEncontrada(EnqueteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(OpcaoNotFoundException.class)
    public ResponseEntity<ErrorResponse> opcaoNaoEncontrada(OpcaoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(UsuarioJaVotouException.class)
    public ResponseEntity<ErrorResponse> usuarioJaVotou(UsuarioJaVotouException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .error(HttpStatus.CONFLICT.getReasonPhrase())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(EnqueteEncerradaException.class)
    public ResponseEntity<ErrorResponse> enqueteEncerrada(EnqueteEncerradaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(ex.getMessage())
                        .build());
    }
}