package com.flight.booking.service;


import com.flight.booking.dto.AircraftDto;
import com.flight.booking.dto.AircraftResponse;
import com.flight.booking.model.Aircraft;
import com.flight.booking.repository.AircraftRepository;
import com.flight.booking.service.Impl.AircraftServiceImpl;
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
public class AircraftServiceTests {

    @Mock
    private AircraftRepository aircraftRepository;

    @InjectMocks
    private AircraftServiceImpl aircraftService;
    @Test
    public void AircraftService_CreateAircraft_ReturnsAircraftDto() {
        Aircraft aircraft = Aircraft.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();
        AircraftDto aircraftDto = AircraftDto.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();

        when(aircraftRepository.save(Mockito.any(Aircraft.class))).thenReturn(aircraft);
        AircraftDto savedAircraft = aircraftService.createAircraft(aircraftDto);

        Assertions.assertThat(savedAircraft).isNotNull();
    }
    @Test
    public void AircraftService_GetAllAircraft_ReturnsResponseDto() {
        Page<Aircraft> aircraft = Mockito.mock(Page.class);

        when(aircraftRepository.findAll(Mockito.any(Pageable.class))).thenReturn(aircraft);

        AircraftResponse saveAircraft = aircraftService.getAllAircraft(1, 10);
        Assertions.assertThat(saveAircraft).isNotNull();
    }
    @Test
    public void AircraftService_FindById_ReturnAircraftDto() {
        Long aircraftId = 1L;
        Aircraft aircraft = Aircraft.builder()
                .aircraftId(1L)
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();

        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.ofNullable(aircraft));

        AircraftDto aircraftReturn = aircraftService.getAircraftById(aircraftId);
        Assertions.assertThat(aircraftReturn).isNotNull();
    }
    @Test
    public void AircraftService_UpdateAircraft_ReturnAircraftDto() {
        Long aircraftId = 1L;
        Aircraft aircraft = Aircraft.builder()
                .aircraftId(1L)
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();
        AircraftDto aircraftDto = AircraftDto.builder()
                .aircraftId(1L)
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();


        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.ofNullable(aircraft));
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        AircraftDto updateAircraft = aircraftService.updateAircraft(aircraftDto,aircraftId);

        Assertions.assertThat(updateAircraft).isNotNull();

    }
    @Test
    public void AircraftService_DeleteAircraftById_ReturnVoid(){
        Long aircraftId = 1L;
        Aircraft aircraft = Aircraft.builder()
                .aircraftId(1L)
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();

        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.ofNullable(aircraft));
        doNothing().when(aircraftRepository).delete(aircraft);

        assertAll(()->aircraftService.deleteAircraftId(aircraftId));
    }
}
