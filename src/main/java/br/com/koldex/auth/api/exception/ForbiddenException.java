package br.com.koldex.auth.api.exception;

public class ForbiddenException
        extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}