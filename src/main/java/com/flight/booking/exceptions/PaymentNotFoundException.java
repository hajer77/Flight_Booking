package com.flight.booking.exceptions;

public class PaymentNotFoundException extends RuntimeException{
    private static final long serialVersionID=1;
    public PaymentNotFoundException(String message){
        super(message);
    }
}
