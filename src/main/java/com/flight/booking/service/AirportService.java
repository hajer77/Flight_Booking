package com.flight.booking.service;

import com.flight.booking.dto.AircraftDto;
import com.flight.booking.dto.AircraftResponse;
import com.flight.booking.dto.AirportDto;
import com.flight.booking.dto.AirportResponse;

public interface AirportService {
    AirportDto createAirport(AirportDto airportDto);
    AirportResponse getAllAirports(int pageNo, int pageSize);
    AirportDto getAirportById(Long airportId);
    AirportDto updateAirport(AirportDto airportDto,Long airportId);
    void deleteAirportId(Long airportId);
}
