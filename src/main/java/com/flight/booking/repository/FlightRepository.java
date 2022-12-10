package com.flight.booking.repository;

import com.flight.booking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    List<Flight> findByAircraftAircraftId(Long aircraftId);

List<Flight> findByDepartureAirportAirportId(Long departureAirportId);
List<Flight> findByDestinationAirportAirportId(Long destinationAirportId);

}
