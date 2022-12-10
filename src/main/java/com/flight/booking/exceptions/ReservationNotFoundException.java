package com.flight.booking.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    private static final long serialVersionID=1;
    public ReservationNotFoundException(String message){
        super(message);
    }
}
