package com.flight.booking.exceptions;

public class TicketNotFoundException extends  RuntimeException {
    private static final long serialVersionID=1;
    public TicketNotFoundException(String message){
        super(message);
    }
}
