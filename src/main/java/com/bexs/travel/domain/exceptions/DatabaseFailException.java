package com.bexs.travel.domain.exceptions;

public class DatabaseFailException extends RuntimeException{
    public DatabaseFailException(String message) {
        super(message);
    }
}
