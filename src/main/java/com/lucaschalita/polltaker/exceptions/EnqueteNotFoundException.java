package com.lucaschalita.polltaker.exceptions;

public class EnqueteNotFoundException extends RuntimeException {
    public EnqueteNotFoundException(String mensagem) {
        super(mensagem);
    }
}