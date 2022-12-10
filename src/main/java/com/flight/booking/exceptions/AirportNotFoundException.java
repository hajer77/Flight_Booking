package com.flight.booking.exceptions;

public class AirportNotFoundException extends RuntimeException{
    private static final long serialVersionID=1;
    public AirportNotFoundException(String message){
        super(message);
    }
}
