package br.com.koldex.auth.api.exception;

public class BusinessException
        extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}