package com.flight.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.booking.dto.*;
import com.flight.booking.model.Aircraft;
import com.flight.booking.model.Airport;
import com.flight.booking.model.Flight;
import com.flight.booking.service.FlightService;
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

@WebMvcTest(controllers =FlightController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class FlightControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Autowired
    private ObjectMapper objectMapper;

    private Flight flight;
    private Airport departureAirport;
    private Airport  destinationAirport;
    private Aircraft aircraft;
    private FlightDto flightDto;
    private AirportDto departureAirportDto;
    private AirportDto  destinationAirportDto;
    private AircraftDto aircraftDto;
    @BeforeEach
    public void init() {
        flight = Flight.builder()
                .flightId(1L)
                .flightNumber("FNumber")
                .economyPrice(200.00)
                .businessPrice(400.00)
                .departureDate(LocalDate.parse("2022-12-20"))
                .arrivalDate(LocalDate.parse("2022-12-20")).build();
        flightDto = FlightDto.builder()
                .flightId(1L)
                .flightNumber("FNumber")
                .economyPrice(200.00)
                .businessPrice(400.00)
                .departureDate(LocalDate.ofEpochDay(2022-12-20))
                .arrivalDate(LocalDate.parse("2022-12-20")).build();
        aircraft = Aircraft.builder()
                .aircraftId(1L)
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();
        aircraftDto  = AircraftDto.builder()
                .aircraftId(1L)
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();
        departureAirport = Airport.builder()
                .airportId(1L)
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();
        departureAirportDto = AirportDto.builder()
                .airportId(1L)
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();
        destinationAirport = Airport.builder()
                .airportId(2L)
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();
        destinationAirportDto = AirportDto.builder()
                .airportId(2L)
                .airportCode("Code2")
                .airportName("Name2")
                .city("city2")
                .state("state2")
                .country("country2").build();
    }
    @Test
    public void FlightController_CreateFlight_ReturnCreated() throws Exception {

        Long aircraftId=1L;
        Long departureAirportId=1L;
        Long destinationAirportId=2L;

        when(flightService.createFlight(aircraftId,departureAirportId,destinationAirportId,flightDto)).thenReturn(flightDto);

        ResultActions response = mockMvc.perform(post("/api/auth/aircraft/1/departureAirport/1/destinationAirport/2/flight")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber", CoreMatchers.is(flightDto.getFlightNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.economyPrice", CoreMatchers.is(flightDto.getEconomyPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.businessPrice", CoreMatchers.is(flightDto.getBusinessPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", CoreMatchers.is(flightDto.getDepartureDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate", CoreMatchers.is(flightDto.getArrivalDate().toString())));
    }
    @Test
    public void FlightController_GetAllFlight_ReturnResponseDto() throws Exception {
        FlightResponse responseDto = FlightResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(flightDto)).build();
        when(flightService.getAllFlights(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/auth/flight/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void FlightController_GetFlightsByAircraftId_ReturnListFlightDto() throws Exception{
        Long aircraftId =1L;
        when(flightService.getFlightsByAircraftId(aircraftId)).thenReturn(Arrays.asList(flightDto));

        ResultActions response = mockMvc.perform(get("/api/auth/aircraft/1/flight")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aircraftDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(flightDto).size())));

    }

    @Test
    public void FlightController_GetFlightsByDepartureAirportId_ReturnListFlightDto() throws Exception{
        Long departureAirportId =1L;
        when(flightService.getFlightsByDepartureAirport(departureAirportId)).thenReturn(Arrays.asList(flightDto));

        ResultActions response = mockMvc.perform(get("/api/auth/departureAirport/1/flight")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aircraftDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(flightDto).size())));

    }
    @Test
    public void FlightController_GetFlightsByDestinationAirportId_ReturnListFlightDto() throws Exception{
        Long destinationAirportId =2L;
        when(flightService.getFlightsByDestinationAirport(destinationAirportId)).thenReturn(Arrays.asList(flightDto));

        ResultActions response = mockMvc.perform(get("/api/auth/destinationAirport/2/flight")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aircraftDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(flightDto).size())));

    }
    @Test
    public void FlightController_GetFlightByAircraftId_ReturnFlightDto() throws Exception{
        Long flightId = 1L;
        Long aircraftId =1L;
        when(flightService.getFlightByIdAircraftId(flightId,aircraftId)).thenReturn((flightDto));

        ResultActions response = mockMvc.perform(get("/api/auth/aircraft/1/flight/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber", CoreMatchers.is(flightDto.getFlightNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.economyPrice", CoreMatchers.is(flightDto.getEconomyPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.businessPrice", CoreMatchers.is(flightDto.getBusinessPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", CoreMatchers.is(flightDto.getDepartureDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate", CoreMatchers.is(flightDto.getArrivalDate().toString())));

    }
    @Test
    public void FlightController_GetFlightByDestinationAirportId_ReturnFlightDto() throws Exception{
        Long flightId = 1L;
        Long destinationAirportId =2L;
        when(flightService.getFlightByIdDestinationAirport(flightId,destinationAirportId)).thenReturn((flightDto));

        ResultActions response = mockMvc.perform(get("/api/auth/destinationAirport/2/flight/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber", CoreMatchers.is(flightDto.getFlightNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.economyPrice", CoreMatchers.is(flightDto.getEconomyPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.businessPrice", CoreMatchers.is(flightDto.getBusinessPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", CoreMatchers.is(flightDto.getDepartureDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate", CoreMatchers.is(flightDto.getArrivalDate().toString())));

    }
    @Test
    public void FlightController_GetFlightByDepartureAirportId_ReturnFlightDto() throws Exception{
        Long flightId = 1L;
        Long departureAirportId =1L;
        when(flightService.getFlightByIdDepartureAirport(flightId,departureAirportId)).thenReturn((flightDto));

        ResultActions response = mockMvc.perform(get("/api/auth/departureAirport/1/flight/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber", CoreMatchers.is(flightDto.getFlightNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.economyPrice", CoreMatchers.is(flightDto.getEconomyPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.businessPrice", CoreMatchers.is(flightDto.getBusinessPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", CoreMatchers.is(flightDto.getDepartureDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate", CoreMatchers.is(flightDto.getArrivalDate().toString())));

    }
    @Test
    public void FlightController_UpdateFlight_ReturnFlightDto() throws Exception {
        Long flightId = 1L;
        Long aircraftId=1L;
        Long departureAirportId=1L;
        Long destinationAirportId=2L;
        when(flightService.updateFlight(aircraftId,departureAirportId,destinationAirportId,flightId,flightDto)).thenReturn(flightDto);

        ResultActions response = mockMvc.perform(put("/api/auth/aircraft/1/departureAirport/1/destinationAirport/2/flight/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber", CoreMatchers.is(flightDto.getFlightNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.economyPrice", CoreMatchers.is(flightDto.getEconomyPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.businessPrice", CoreMatchers.is(flightDto.getBusinessPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", CoreMatchers.is(flightDto.getDepartureDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate", CoreMatchers.is(flightDto.getArrivalDate().toString())));
    }
    @Test
    public void FlightController_DeleteFlight_ReturnVoid() throws Exception {
        Long flightId = 1L;
        Long aircraftId=1L;
        Long departureAirportId=1L;
        Long destinationAirportId=2L;
        doNothing().when(flightService).deleteFlight(aircraftId,departureAirportId,destinationAirportId,flightId);

        ResultActions response = mockMvc.perform(delete("/api/auth/aircraft/1/departureAirport/1/destinationAirport/2/flight/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }


}
