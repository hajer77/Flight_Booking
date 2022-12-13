package com.flight.booking.service;


import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.PassengerDto;
import com.flight.booking.dto.TicketDto;
import com.flight.booking.model.Flight;
import com.flight.booking.model.Passenger;
import com.flight.booking.model.Ticket;
import com.flight.booking.repository.FlightRepository;
import com.flight.booking.repository.PassengerRepository;
import com.flight.booking.repository.TicketRepository;
import com.flight.booking.service.Impl.PassengerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTests {

    @Mock
    PassengerRepository passengerRepository;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    FlightRepository flightRepository;

    @InjectMocks
    PassengerServiceImpl passengerService;

    private Passenger passenger;
    private Ticket ticket;
    private Flight flight;
    private PassengerDto passengerDto;
    private TicketDto ticketDto;
    private FlightDto flightDto;

    @BeforeEach
    public void init() {
        passenger = Passenger.builder()
                .passengerId(1L)
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("+25693333333")
                .passportNumber("A4821566")
                .address("address")
                .email("email@gmail.com")
                .age(30).build();
        passengerDto = PassengerDto.builder()
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("+25693333333")
                .passportNumber("A4821566")
                .address("address")
                .email("email@gmail.com")
                .age(30).build();
        ticket = Ticket.builder()
                .tickedId(1l)
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();
        ticketDto = TicketDto.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();
        flight = Flight.builder()
                .flightId(1L)
                .flightNumber("FNumber")
                .economyPrice(200.00)
                .businessPrice(400.00)
                .departureDate(LocalDate.parse("2022-12-20"))
                .arrivalDate(LocalDate.parse("2022-12-20")).build();
        flightDto = FlightDto.builder()
                .flightNumber("FNumber")
                .economyPrice(200.00)
                .businessPrice(400.00)
                .departureDate(LocalDate.parse("2022-12-20"))
                .arrivalDate(LocalDate.parse("2022-12-20")).build();
    }
    @Test
    public void PassengerService_CreatePassenger_ReturnsPassengerDto() {
        when(ticketRepository.findById(ticket.getTickedId())).thenReturn(Optional.of(ticket));
        when(flightRepository.findById(flight.getFlightId())).thenReturn(Optional.of(flight));
        when(passengerRepository.save(Mockito.any(Passenger.class))).thenReturn(passenger);
        PassengerDto savedPassenger =  passengerService.createPassenger(ticket.getTickedId(),flight.getFlightId(),passengerDto);

        Assertions.assertThat(savedPassenger).isNotNull();
    }
    @Test
    public void PassengerService_GetPassengersByFlightId_ReturnListPassengerDto(){
        Long flightId =1L;
        when(passengerRepository.findByFlightFlightId(flightId)).thenReturn(Arrays.asList(passenger));

        List<PassengerDto> passengerReturn = passengerService.getPassengersByFlightId(flightId);

        Assertions.assertThat(passengerReturn).isNotNull();
    }
    @Test
    public void PassengerService_GetPassengerByTicketId_ReturnFlightDto(){
        Long passengerId=1L;
        Long ticketId=1L;

        passenger.setTicket(ticket);
        passenger.setFlight(flight);

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));


        System.out.println(passengerRepository.findById(passengerId));
        PassengerDto passengerReturn = passengerService.getPassengerById(passengerId,ticketId);

        System.out.println(passengerReturn);

        Assertions.assertThat(passengerReturn).isNotNull();
    }
    @Test
    public void PassengerService_UpdatePassenger_ReturnPassengerDto(){
        Long passengerId=1L;
        Long ticketId=1L;
        Long flightId =1L;

        flight.setPassengers(Arrays.asList(passenger));


        passenger.setTicket(ticket);
        passenger.setFlight(flight);


        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(passengerRepository.save(passenger)).thenReturn(passenger);

        PassengerDto updatePassenger = passengerService.updatePassenger(ticketId,flightId,passengerId,passengerDto);
        Assertions.assertThat(updatePassenger).isNotNull();



    }
    @Test
    public void PassengerService_deletePassengerById_ReturnVoid(){
        Long passengerId=1L;
        Long ticketId=1L;
        Long flightId =1L;
        flight.setPassengers(Arrays.asList(passenger));
        passenger.setTicket(ticket);
        passenger.setFlight(flight);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));

        assertAll(()-> passengerService.deletePassenger(ticketId,flightId,passengerId));

    }

}
