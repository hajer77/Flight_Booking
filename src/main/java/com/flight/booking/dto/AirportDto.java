package com.flight.booking.dto;


import lombok.Data;

@Data
public class AirportDto {

    private Long airportId;

    private String airportCode;
    private String airportName;
    private String city;
    private String state;
    private String country;

}

