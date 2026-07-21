package com.lucaschalita.polltaker.exceptions;

public class OpcaoNotFoundException extends RuntimeException {
    public OpcaoNotFoundException(String mensagem) {
        super(mensagem);
    }
}