package com.flight.booking.service;

import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.PassengerDto;

import java.util.List;

public interface PassengerService {
    PassengerDto createPassenger(Long ticketId,Long flightId,PassengerDto passengerDto);
    List<PassengerDto> getPassengersByFlightId(Long flightId);

    PassengerDto getPassengerById(Long passengerId , Long ticketId);
    PassengerDto updatePassenger(Long ticketId,Long flightId,Long passengerId,PassengerDto passengerDto);

    void deletePassenger(Long ticketId,Long flightId,Long passengerId);

}
