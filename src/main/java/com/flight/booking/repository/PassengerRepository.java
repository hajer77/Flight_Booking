package com.flight.booking.repository;


import com.flight.booking.model.Flight;
import com.flight.booking.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository  extends JpaRepository<Passenger,Long> {
    List<Passenger> findByFlightFlightId(Long flightId);

}
