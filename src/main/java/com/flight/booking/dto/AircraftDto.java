package com.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AircraftDto {
    private Long aircraftId;

    private String manufacturer;
    private String model;
    private Integer passengerCapacity;

}
