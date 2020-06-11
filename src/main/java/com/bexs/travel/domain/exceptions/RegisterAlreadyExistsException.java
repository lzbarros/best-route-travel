package com.bexs.travel.domain.exceptions;

public class RegisterAlreadyExistsException extends RuntimeException {
    public RegisterAlreadyExistsException(String message) {
        super(message);
    }
}
