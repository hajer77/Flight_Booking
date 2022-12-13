package com.flight.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TicketDto {

    private Long tickedId;
    private String seatNumber;
    private Double maxKg;
    private String typeOfTicket;
    private Double price;
}
