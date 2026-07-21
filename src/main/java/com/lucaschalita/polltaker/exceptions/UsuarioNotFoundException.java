package com.lucaschalita.polltaker.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String mensagem) {
        super(mensagem);
    }
}