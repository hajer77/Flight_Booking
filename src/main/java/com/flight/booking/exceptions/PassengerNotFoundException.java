package com.flight.booking.exceptions;

public class PassengerNotFoundException extends RuntimeException{
    private static final long serialVersionID=1;
    public PassengerNotFoundException(String message){
        super(message);
    }
}
