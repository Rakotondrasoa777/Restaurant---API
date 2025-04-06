package com.tojo.examerestaurantspringboot.service.exception;

public class ClientException extends RuntimeException {
    public ClientException(Exception message) {
        super(message);
    }

    public ClientException(String message) {
        super(message);
    }
}
