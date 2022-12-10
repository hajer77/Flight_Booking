package com.flight.booking.exceptions;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionID=1;
    public UserNotFoundException(String message){
        super(message);
    }
}
