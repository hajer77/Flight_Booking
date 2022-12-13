package com.flight.booking.dto;

import com.flight.booking.model.Aircraft;
import com.flight.booking.model.Airport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FlightDto {

    private Long flightId;
    private String flightNumber;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Double economyPrice;
    private Double businessPrice;

}
