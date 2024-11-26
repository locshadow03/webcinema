package com.webcinema.extension;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
    super(message);
    }
}
