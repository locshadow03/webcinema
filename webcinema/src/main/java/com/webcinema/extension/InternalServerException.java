package com.webcinema.extension;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String message) {
        super(message);
    }
}