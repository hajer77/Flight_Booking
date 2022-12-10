package com.flight.booking.exceptions;

public class AircraftNotFoundException extends RuntimeException{
    private static final long serialVersionID=1;
    public AircraftNotFoundException(String message){
        super(message);
    }
}
