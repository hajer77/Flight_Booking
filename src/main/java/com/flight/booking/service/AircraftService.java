package com.flight.booking.service;

import com.flight.booking.dto.AircraftDto;
import com.flight.booking.dto.AircraftResponse;
import com.flight.booking.dto.TicketDto;
import com.flight.booking.dto.TicketResponse;

import java.util.List;

public interface AircraftService {
    AircraftDto createAircraft(AircraftDto aircraftDto);
    AircraftResponse getAllAircraft(int pageNo, int pageSize);

    AircraftDto getAircraftById(Long aircraftId);
    AircraftDto updateAircraft(AircraftDto aircraftDto,Long aircraftId);
    void deleteAircraftId(Long aircraftId);
}
