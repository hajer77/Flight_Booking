package com.flight.booking.exceptions;

public class FlightNotFoundException extends RuntimeException{
    private static final long serialVersionID=1;
    public FlightNotFoundException(String message){
        super(message);
    }
}
