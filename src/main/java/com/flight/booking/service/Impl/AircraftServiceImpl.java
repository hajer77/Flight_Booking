package com.flight.booking.service.Impl;

import com.flight.booking.dto.*;
import com.flight.booking.exceptions.AircraftNotFoundException;
import com.flight.booking.exceptions.AirportNotFoundException;
import com.flight.booking.model.Aircraft;
import com.flight.booking.model.Airport;
import com.flight.booking.model.Ticket;
import com.flight.booking.repository.AircraftRepository;
import com.flight.booking.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AircraftServiceImpl implements AircraftService {

    private AircraftRepository aircraftRepository;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public AircraftDto createAircraft(AircraftDto aircraftDto) {
        Aircraft aircraft = mapToEntity(aircraftDto) ;
        Aircraft newAircraft = aircraftRepository.save(aircraft);
        return mapToDto(newAircraft);
    }

    @Override
    public AircraftDto getAircraftById(Long aircraftId) {
        Aircraft aircraft=aircraftRepository.findById(aircraftId).orElseThrow(() -> new AircraftNotFoundException("Aircraft could not be found"));
        return mapToDto(aircraft);
    }

    @Override
    public AircraftResponse getAllAircraft(int pageNo , int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Aircraft> aircraft =aircraftRepository.findAll(pageable);
        List<Aircraft> ListOfAircraft = aircraft.getContent();
        List<AircraftDto> content = ListOfAircraft.stream().map(aircraft1 ->mapToDto(aircraft1) ).collect(Collectors.toList());

        AircraftResponse aircraftResponse = new AircraftResponse();
        aircraftResponse.setContent(content);
        aircraftResponse.setPageNo(aircraft.getNumber());
        aircraftResponse.setPageSize(aircraft.getSize());
        aircraftResponse.setTotalElements(aircraft.getTotalElements());
        aircraftResponse.setTotalPages(aircraft.getTotalPages());
        aircraftResponse.setLast(aircraft.isLast());
        return aircraftResponse;

    }
    @Override
    public AircraftDto updateAircraft(AircraftDto aircraftDto, Long aircraftId) {
        Aircraft aircraft =aircraftRepository.findById(aircraftId).orElseThrow(()-> new AircraftNotFoundException("Aircraft could not be updated"));
        aircraft.setModel(aircraftDto.getModel());
        aircraft.setManufacturer(aircraftDto.getManufacturer());
        aircraft.setPassengerCapacity(aircraftDto.getPassengerCapacity());

        Aircraft updatedAircraft  = aircraftRepository.save(aircraft);
        return mapToDto(updatedAircraft );

    }

    @Override
    public void deleteAircraftId(Long aircraftId) {
        Aircraft aircraft =aircraftRepository.findById(aircraftId).orElseThrow(()-> new AircraftNotFoundException("Aircraft could not be deleted"));
        aircraftRepository.delete(aircraft);
    }

    private AircraftDto mapToDto (Aircraft aircraft){
        AircraftDto aircraftDto = new AircraftDto();
        aircraftDto.setAircraftId(aircraft.getAircraftId());
        aircraftDto.setModel(aircraft.getModel());
        aircraftDto.setManufacturer(aircraft.getManufacturer());
        aircraftDto.setPassengerCapacity(aircraft.getPassengerCapacity());
        return  aircraftDto;

    }
    private Aircraft mapToEntity(AircraftDto aircraftDto){
        Aircraft aircraft = new Aircraft();
        aircraft.setModel(aircraftDto.getModel());
        aircraft.setManufacturer(aircraftDto.getManufacturer());
        aircraft.setPassengerCapacity(aircraftDto.getPassengerCapacity());
        return  aircraft;
    }
}
