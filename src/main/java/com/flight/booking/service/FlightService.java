package com.flight.booking.service;

import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.FlightResponse;
import com.flight.booking.dto.TicketResponse;

import java.util.List;

public interface FlightService {
    FlightDto createFlight(Long aircraftId ,Long departureAirportId ,Long destinationAirportId,FlightDto flightDto);
    FlightResponse getAllFlights(int pageNo, int pageSize);

    List<FlightDto> getFlightsByAircraftId(Long aircraftId);
    List<FlightDto> getFlightsByDepartureAirport(Long departureAirport );
    List<FlightDto> getFlightsByDestinationAirport(Long destinationAirport );
    FlightDto getFlightByIdAircraftId(Long flightId,Long aircraftId);
    FlightDto getFlightByIdDepartureAirport(Long flightId,Long departureAirport);

    FlightDto getFlightByIdDestinationAirport(Long flightId,Long destinationAirport);

    FlightDto updateFlight(Long aircraftId ,Long departureAirportId ,Long destinationAirportId,Long flightId,FlightDto flightDto);

    void deleteFlight(Long aircraftId ,Long departureAirportId ,Long destinationAirportId,Long flightId);
}
