package com.flight.booking.service.Impl;

import com.flight.booking.dto.*;
import com.flight.booking.exceptions.AirportNotFoundException;
import com.flight.booking.exceptions.TicketNotFoundException;
import com.flight.booking.model.Aircraft;
import com.flight.booking.model.Airport;
import com.flight.booking.model.Ticket;
import com.flight.booking.repository.AirportRepository;
import com.flight.booking.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {

    private AirportRepository airportRepository;
    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public AirportResponse getAllAirports(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Airport> airports =airportRepository.findAll(pageable);
        List<Airport> ListOfAirport = airports.getContent();
        List<AirportDto> content = ListOfAirport.stream().map(airport1 ->mapToDto(airport1) ).collect(Collectors.toList());

        AirportResponse airportResponse = new AirportResponse();
        airportResponse.setContent(content);
        airportResponse.setPageNo(airports.getNumber());
        airportResponse.setPageSize(airports.getSize());
        airportResponse.setTotalElements(airports.getTotalElements());
        airportResponse.setTotalPages(airports.getTotalPages());
        airportResponse.setLast(airports.isLast());
        return airportResponse;
    }

    @Override
    public AirportDto createAirport(AirportDto airportDto) {
        Airport airport = mapToEntity(airportDto) ;
        Airport newAirport = airportRepository.save(airport);
        return mapToDto(newAirport);
    }

    @Override
    public AirportDto getAirportById(Long airportId) {
        Airport airport=airportRepository.findById(airportId).orElseThrow(() -> new AirportNotFoundException("Airport could not be found"));
        return mapToDto(airport);
    }
    @Override
    public AirportDto updateAirport(AirportDto airportDto, Long airportId) {
        Airport airport =airportRepository.findById(airportId).orElseThrow(()-> new AirportNotFoundException("Airport could not be updated"));
        airport.setAirportCode(airportDto.getAirportCode());
        airport.setAirportName(airportDto.getAirportName());
        airport.setCity(airportDto.getCity());
        airport.setState(airportDto.getState());
        airport.setCountry(airportDto.getCountry());

        Airport updatedAirport = airportRepository.save(airport);
        return mapToDto(updatedAirport);

    }

    @Override
    public void deleteAirportId(Long airportId) {
        Airport airport =airportRepository.findById(airportId).orElseThrow(()-> new AirportNotFoundException("Airport could not be deleted"));
        airportRepository.delete(airport);
    }

    private AirportDto mapToDto (Airport airport){
        AirportDto airportDto = new AirportDto();
        airportDto.setAirportId(airport.getAirportId());
        airportDto.setAirportCode(airport.getAirportCode());
        airportDto.setAirportName(airport.getAirportName());
        airportDto.setCity(airport.getCity());
        airportDto.setState(airport.getState());
        airportDto.setCountry(airport.getCountry());

        return  airportDto;

    }
    private Airport mapToEntity (AirportDto airportDto){
        Airport airport = new Airport();
        airport.setAirportCode(airportDto.getAirportCode());
        airport.setAirportName(airportDto.getAirportName());
        airport.setCity(airportDto.getCity());
        airport.setState(airportDto.getState());
        airport.setCountry(airportDto.getCountry());

        return  airport;

    }

}
