package com.flight.booking.dto;

import lombok.Data;

@Data
public class AircraftDto {
    private Long aircraftId;

    private String manufacturer;
    private String model;
    private Integer passengerCapacity;

}
