package com.codebandit.authsimplified.exceptions;

public class SecureResourceNotFoundException extends RuntimeException {
    String message;

    public SecureResourceNotFoundException(String message) {
        super(message);
    }

    public SecureResourceNotFoundException() {
        super("Resource not found");
    }
}
