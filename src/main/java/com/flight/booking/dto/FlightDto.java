package com.flight.booking.dto;

import com.flight.booking.model.Aircraft;
import com.flight.booking.model.Airport;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightDto {

    private Long flightId;
    private String flightNumber;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Double economyPrice;
    private Double businessPrice;

}
