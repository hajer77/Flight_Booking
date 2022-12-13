package com.flight.booking.service;


import com.flight.booking.dto.AirportDto;
import com.flight.booking.dto.AirportResponse;
import com.flight.booking.model.Airport;
import com.flight.booking.repository.AirportRepository;
import com.flight.booking.service.Impl.AirportServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTests {

     @Mock
     private AirportRepository  airportRepository;

     @InjectMocks
     private AirportServiceImpl airportService;

    @Test
    public void AirportService_CreateAirport_ReturnsAirportDto() {
        Airport airport = Airport.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();
        AirportDto airportDto = AirportDto.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();

        when(airportRepository.save(Mockito.any(Airport.class))).thenReturn(airport);
        AirportDto savedAirport = airportService.createAirport(airportDto);

        Assertions.assertThat(savedAirport).isNotNull();
    }
    @Test
    public void AirportService_GetAllAirport_ReturnsResponseDto() {
        Page<Airport> airport = Mockito.mock(Page.class);

        when(airportRepository.findAll(Mockito.any(Pageable.class))).thenReturn(airport);

        AirportResponse saveAirport = airportService.getAllAirports(1, 10);
        Assertions.assertThat(saveAirport).isNotNull();
    }

    @Test
    public void AirportService_FindById_ReturnAirportDto() {
        Long airportId = 1L;
        Airport airport = Airport.builder()
                .airportId(1L)
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();

        when(airportRepository.findById(airportId)).thenReturn(Optional.ofNullable(airport));

        AirportDto airportReturn = airportService.getAirportById(airportId);
        Assertions.assertThat(airportReturn).isNotNull();
    }
    @Test
    public void AirportService_UpdateAirport_ReturnAirportDto() {
        Long airportId = 1L;
        Airport airport = Airport.builder()
                .airportId(1L)
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();
        AirportDto airportDto = AirportDto.builder()
                .airportId(1L)
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();


        when(airportRepository.findById(airportId)).thenReturn(Optional.ofNullable(airport));
        when(airportRepository.save(airport)).thenReturn(airport);
        AirportDto updateAirport = airportService.updateAirport(airportDto,airportId);

        Assertions.assertThat(updateAirport).isNotNull();

    }
    @Test
    public void AirportService_DeleteAirportById_ReturnVoid(){
        Long airportId = 1L;
        Airport airport = Airport.builder()
                .airportId(1L)
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();

        when(airportRepository.findById(airportId)).thenReturn(Optional.ofNullable(airport));
        doNothing().when(airportRepository).delete(airport);

        assertAll(()->airportService.deleteAirportId(airportId));
    }
}
