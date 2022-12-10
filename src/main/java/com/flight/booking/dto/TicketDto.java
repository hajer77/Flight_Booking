package com.flight.booking.dto;


import lombok.Data;

@Data
public class TicketDto {

    private Long tickedId;
    private String seatNumber;
    private Double maxKg;
    private String typeOfTicket;
    private Double price;
}
