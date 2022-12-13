package com.flight.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AirportDto {

    private Long airportId;

    private String airportCode;
    private String airportName;
    private String city;
    private String state;
    private String country;

}

