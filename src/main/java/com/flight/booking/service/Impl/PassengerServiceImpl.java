package com.flight.booking.service.Impl;

import com.flight.booking.dto.PassengerDto;
import com.flight.booking.exceptions.FlightNotFoundException;
import com.flight.booking.exceptions.PassengerNotFoundException;
import com.flight.booking.exceptions.TicketNotFoundException;
import com.flight.booking.model.Flight;
import com.flight.booking.model.Passenger;
import com.flight.booking.model.Ticket;
import com.flight.booking.repository.FlightRepository;
import com.flight.booking.repository.PassengerRepository;
import com.flight.booking.repository.TicketRepository;
import com.flight.booking.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    private PassengerRepository passengerRepository;
    private TicketRepository   ticketRepository;
    private FlightRepository flightRepository;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.passengerRepository = passengerRepository;
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public List<PassengerDto> getPassengersByFlightId(Long flightId) {
        List<Passenger> passengers =passengerRepository.findByFlightFlightId(flightId);
        return passengers.stream().map(passenger -> mapToDto(passenger)).collect(Collectors.toList());
    }

    @Override
    public PassengerDto getPassengerById(Long passengerId ,Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket with associated Passenger not found"));
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new PassengerNotFoundException("Passenger with associated Ticket not found"));
        if (!(passenger.getTicket().getTickedId()).equals (ticket.getTickedId())) {
            throw new PassengerNotFoundException("This Passenger does not belond to a Ticket");

        }

        return mapToDto(passenger);
    }

    @Override
    public PassengerDto createPassenger(Long ticketId,Long flightId,PassengerDto passengerDto) {
        Passenger passenger = mapToEntity(passengerDto);
        Ticket ticket= ticketRepository.findById(ticketId).orElseThrow(()-> new TicketNotFoundException("Ticket with associated Passenger not found"));
        Flight flight = flightRepository.findById(flightId).orElseThrow(()-> new FlightNotFoundException("Flight with associated Passenger not found"));
       passenger.setTicket(ticket);
       passenger.setFlight(flight);
       Passenger newPassenger = passengerRepository.save(passenger);
       return mapToDto(newPassenger);

    }

    @Override
    public PassengerDto updatePassenger(Long ticketId, Long flightId,Long passengerId ,PassengerDto passengerDto) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(()->new PassengerNotFoundException("Passenger not Found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("Ticket not Found"));
        Flight flight= flightRepository.findById(flightId).orElseThrow(()->new FlightNotFoundException("Flight not Found"));

        if(!((passenger.getTicket().getTickedId()).equals(ticket.getTickedId())) ||
                (!((passenger.getFlight().getFlightId())).equals(flight.getFlightId()))) {
            throw new PassengerNotFoundException("This Passenger does not exist");
        }
        passenger.setFirstName(passengerDto.getFirstName());
        passenger.setLastName(passengerDto.getLastName());
        passenger.setPassportNumber(passengerDto.getPassportNumber());
        passenger.setEmail(passengerDto.getEmail());
        passenger.setPhoneNumber(passengerDto.getPhoneNumber());
        passenger.setAddress(passengerDto.getAddress());
        passenger.setAge(passengerDto.getAge());

        Passenger updatePassenger = passengerRepository.save(passenger);
         return mapToDto(updatePassenger);

    }

    @Override
    public void deletePassenger(Long ticketId, Long flightId, Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(()->new PassengerNotFoundException("Passenger not Found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("Ticket not Found"));
        Flight flight= flightRepository.findById(flightId).orElseThrow(()->new FlightNotFoundException("Flight not Found"));

        if(!((passenger.getTicket().getTickedId()).equals(ticket.getTickedId())) ||
                (!((passenger.getFlight().getFlightId())).equals(flight.getFlightId()))) {
            throw new PassengerNotFoundException("This Passenger does not exist");
        }


        passengerRepository.delete(passenger);

    }

    private PassengerDto mapToDto(Passenger passenger){

        PassengerDto passengerDto = new PassengerDto();

        passengerDto.setPassengerId(passenger.getPassengerId());
        passengerDto.setFirstName(passenger.getFirstName());
        passengerDto.setLastName(passenger.getLastName());
        passengerDto.setPassportNumber(passenger.getPassportNumber());
        passengerDto.setPhoneNumber(passenger.getPhoneNumber());
        passengerDto.setEmail(passenger.getEmail());
        passengerDto.setAddress(passenger.getAddress());
        passengerDto.setAge(passenger.getAge());

        return passengerDto;
    }
    private Passenger mapToEntity(PassengerDto passengerDto){

        Passenger passenger = new Passenger();

        passenger.setFirstName(passengerDto.getFirstName());
        passenger.setLastName(passengerDto.getLastName());
        passenger.setPassportNumber(passengerDto.getPassportNumber());
        passenger.setEmail(passengerDto.getEmail());
        passenger.setPhoneNumber(passengerDto.getPhoneNumber());
        passenger.setAddress(passengerDto.getAddress());
        passenger.setAge(passengerDto.getAge());

        return passenger;

    }
}
