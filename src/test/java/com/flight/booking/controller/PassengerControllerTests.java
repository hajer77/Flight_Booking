package com.flight.booking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.PassengerDto;
import com.flight.booking.dto.TicketDto;
import com.flight.booking.model.Flight;
import com.flight.booking.model.Passenger;
import com.flight.booking.model.Ticket;
import com.flight.booking.service.PassengerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers =PassengerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PassengerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassengerService passengerService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void PassengerController_CreatePassenger_ReturnCreated() throws Exception {


        Long ticketId=1L;
        Long flightId =1L;

        when(passengerService.createPassenger(ticketId,flightId,passengerDto)).thenReturn(passengerDto);

        ResultActions response = mockMvc.perform(post("/api/auth/ticket/1/flight/1/passenger")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(passengerDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(passengerDto.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber", CoreMatchers.is(passengerDto.getPassportNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(passengerDto.getPhoneNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(passengerDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", CoreMatchers.is(passengerDto.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(passengerDto.getAge())));
    }
    @Test
    public void PassengerController_GetPassengersByFlightId_ReturnListPassengerDto() throws Exception{
        Long flightId =1L;
        when(passengerService.getPassengersByFlightId(flightId)).thenReturn(Arrays.asList(passengerDto));

        ResultActions response = mockMvc.perform(get("/api/auth/flight/1/passenger")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(passengerDto).size())));

    }
    @Test
    public void PassengerController_GetPassengerByTicketId_ReturnPassengerDto() throws Exception{
        Long passengerId = 1L;
        Long ticketId =1L;
        when(passengerService.getPassengerById(passengerId,ticketId)).thenReturn((passengerDto));

        ResultActions response = mockMvc.perform(get("/api/auth/ticket/1/passenger/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(passengerDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(passengerDto.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber", CoreMatchers.is(passengerDto.getPassportNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(passengerDto.getPhoneNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(passengerDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", CoreMatchers.is(passengerDto.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(passengerDto.getAge())));

    }
    @Test
    public void PassengerController_UpdatePassenger_ReturnPassengerDto() throws Exception {
        Long passengerId = 1L;
        Long ticketId = 1L;
        Long flightId = 1L;

        when(passengerService.updatePassenger(ticketId, flightId, passengerId, passengerDto)).thenReturn(passengerDto);

        ResultActions response = mockMvc.perform(put("/api/auth/ticket/1/flight/1/passenger/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(passengerDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(passengerDto.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber", CoreMatchers.is(passengerDto.getPassportNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(passengerDto.getPhoneNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(passengerDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", CoreMatchers.is(passengerDto.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(passengerDto.getAge())));
    }
    @Test
    public void PassengerController_DeletePassenger_ReturnVoid() throws Exception {
        Long passengerId = 1L;
        Long ticketId = 1L;
        Long flightId = 1L;
        doNothing().when(passengerService).deletePassenger(ticketId,flightId,passengerId);

        ResultActions response = mockMvc.perform(delete("/api/auth/ticket/1/flight/1/passenger/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}
