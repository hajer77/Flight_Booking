package com.flight.booking.service;


import com.flight.booking.dto.AircraftDto;
import com.flight.booking.dto.AirportDto;
import com.flight.booking.dto.FlightDto;
import com.flight.booking.model.Aircraft;
import com.flight.booking.model.Airport;
import com.flight.booking.model.Flight;
import com.flight.booking.repository.AircraftRepository;
import com.flight.booking.repository.AirportRepository;
import com.flight.booking.repository.FlightRepository;
import com.flight.booking.service.Impl.FlightServiceImpl;
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
public class FlightServiceTests {

    @Mock
    FlightRepository flightRepository;
    @Mock
    AircraftRepository aircraftRepository;
    @Mock
    AirportRepository airportRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

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
                .flightNumber("FNumber")
                .economyPrice(200.00)
                .businessPrice(400.00)
                .departureDate(LocalDate.parse("2022-12-20"))
                .arrivalDate(LocalDate.parse("2022-12-20")).build();
        aircraft = Aircraft.builder()
                .aircraftId(1L)
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();
        aircraftDto  = AircraftDto.builder()
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
                .airportCode("Code2")
                .airportName("Name2")
                .city("city2")
                .state("state2")
                .country("country2").build();
    }

    @Test
    public void FlightService_CreateFlight_ReturnsFlightDto() {
        when(aircraftRepository.findById(aircraft.getAircraftId())).thenReturn(Optional.of(aircraft));
        when(airportRepository.findById(departureAirport.getAirportId())).thenReturn(Optional.of(departureAirport));
        when(airportRepository.findById(destinationAirport.getAirportId())).thenReturn((Optional.of(destinationAirport)));
        when(flightRepository.save(Mockito.any(Flight.class))).thenReturn(flight);
        FlightDto savedFlight = flightService.createFlight(aircraft.getAircraftId(),departureAirport.getAirportId(),destinationAirport.getAirportId(),flightDto);

        Assertions.assertThat(savedFlight).isNotNull();
    }
    @Test
    public void FlightService_GetFlightsByAircraftId_ReturnListFlightDto(){
        Long aircraftId =1L;
        when(flightRepository.findByAircraftAircraftId(aircraftId)).thenReturn(Arrays.asList(flight));

        List<FlightDto> flightReturn = flightService.getFlightsByAircraftId(aircraftId);

        Assertions.assertThat(flightReturn).isNotNull();
    }

    @Test
    public void FlightService_GetFlightsByDepartureAirportId_ReturnListFlightDto(){
        Long departureAirportId =1L;
        when(flightRepository.findByDepartureAirportAirportId(departureAirportId)).thenReturn(Arrays.asList(flight));

        List<FlightDto> flightReturn = flightService.getFlightsByDepartureAirport(departureAirportId);

        Assertions.assertThat(flightReturn).isNotNull();
    }
    @Test
    public void FlightService_GetFlightsByDestinationAirportId_ReturnListFlightDto(){
        Long destinationAirportId =2L;
        when(flightRepository.findByDestinationAirportAirportId(destinationAirportId)).thenReturn(Arrays.asList(flight));

        List<FlightDto> flightReturn = flightService.getFlightsByDestinationAirport(destinationAirportId);

        Assertions.assertThat(flightReturn).isNotNull();
    }
    @Test
    public void FlightService_GetFlightByAircraftId_ReturnFlightDto(){
        Long flightId=1L;
        Long aircraftId=1L;

        flight.setAircraft(aircraft);
        flight.setDepartureAirport(departureAirport);
        flight.setDestinationAirport(destinationAirport);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));

        System.out.println(flightRepository.findById(flightId));
        FlightDto flightReturn = flightService.getFlightByIdAircraftId(flightId,aircraftId);

        System.out.println(flightReturn);

        Assertions.assertThat(flightReturn).isNotNull();
    }

    @Test
    public void FlightService_GetFlightByDepartureAirportId_ReturnFlightDto(){
        Long flightId=1L;
        Long departureAirportId=1L;

        flight.setAircraft(aircraft);
        flight.setDepartureAirport(departureAirport);
        flight.setDestinationAirport(destinationAirport);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(airportRepository.findById(departureAirportId)).thenReturn(Optional.of(departureAirport));

        System.out.println(flightRepository.findById(flightId));
        FlightDto flightReturn = flightService.getFlightByIdDepartureAirport(flightId,departureAirportId);

        System.out.println(flightReturn);

        Assertions.assertThat(flightReturn).isNotNull();
    }
    @Test
    public void FlightService_GetFlightByDestinationAirportId_ReturnFlightDto(){
        Long flightId=1L;
        Long destinationAirportId=2L;

        flight.setAircraft(aircraft);
        flight.setDepartureAirport(departureAirport);
        flight.setDestinationAirport(destinationAirport);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(airportRepository.findById(destinationAirportId)).thenReturn(Optional.of(destinationAirport));

        System.out.println(flightRepository.findById(flightId));
        FlightDto flightReturn = flightService.getFlightByIdDestinationAirport(flightId,destinationAirportId);

        System.out.println(flightReturn);

        Assertions.assertThat(flightReturn).isNotNull();
    }
    @Test
    public void FlightService_UpdateFlight_ReturnFlightDto(){
        Long flightId=1L;
        Long aircraftId=1L;
        Long departureAirportId=1L;
        Long destinationAirportId=2L;
        aircraft.setFlights(Arrays.asList(flight));
        departureAirport.setFlightsDepartureAirport(Arrays.asList(flight));
        destinationAirport.setFlightsDestinationAirport(Arrays.asList(flight));
        flight.setAircraft(aircraft);
        flight.setDepartureAirport(departureAirport);
        flight.setDestinationAirport(destinationAirport);


        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));
        when(airportRepository.findById(departureAirportId)).thenReturn(Optional.of(departureAirport));
        when(airportRepository.findById(destinationAirportId)).thenReturn(Optional.of(destinationAirport));
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(flightRepository.save(flight)).thenReturn(flight);

        FlightDto updateFlight = flightService.updateFlight(aircraftId,departureAirportId,destinationAirportId,flightId,flightDto);
        Assertions.assertThat(updateFlight).isNotNull();



    }
    @Test
    public void FlightService_deleteFlightById_ReturnVoid(){
        Long flightId=1L;
        Long aircraftId=1L;
        Long departureAirportId=1L;
        Long destinationAirportId=2L;
        aircraft.setFlights(Arrays.asList(flight));
        departureAirport.setFlightsDepartureAirport(Arrays.asList(flight));
        destinationAirport.setFlightsDestinationAirport(Arrays.asList(flight));
        flight.setAircraft(aircraft);
        flight.setDepartureAirport(departureAirport);
        flight.setDestinationAirport(destinationAirport);

        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));
        when(airportRepository.findById(departureAirportId)).thenReturn(Optional.of(departureAirport));
        when(airportRepository.findById(destinationAirportId)).thenReturn(Optional.of(destinationAirport));
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));

        assertAll(()-> flightService.deleteFlight(aircraftId,departureAirportId,destinationAirportId,flightId));

    }
}
