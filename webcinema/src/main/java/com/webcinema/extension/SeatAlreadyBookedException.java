package com.webcinema.extension;

public class SeatAlreadyBookedException extends RuntimeException{
    public SeatAlreadyBookedException(String message){
        super(message);
    }
}
