package com.flight.booking.service.Impl;

import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.FlightResponse;
import com.flight.booking.dto.TicketDto;
import com.flight.booking.dto.TicketResponse;
import com.flight.booking.exceptions.AircraftNotFoundException;
import com.flight.booking.exceptions.AirportNotFoundException;
import com.flight.booking.exceptions.FlightNotFoundException;
import com.flight.booking.model.Aircraft;
import com.flight.booking.model.Airport;
import com.flight.booking.model.Flight;
import com.flight.booking.model.Ticket;
import com.flight.booking.repository.AircraftRepository;
import com.flight.booking.repository.AirportRepository;
import com.flight.booking.repository.FlightRepository;
import com.flight.booking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;
    private AircraftRepository aircraftRepository;
    private AirportRepository airportRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, AircraftRepository aircraftRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
    }
    @Override
    public FlightResponse getAllFlights(int pageNo , int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Flight> flights =flightRepository.findAll(pageable);
        List<Flight> ListOfFlight = flights.getContent();
        List<FlightDto> content = ListOfFlight.stream().map(flight1 ->mapToDto(flight1) ).collect(Collectors.toList());

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setContent(content);
        flightResponse.setPageNo(flights.getNumber());
        flightResponse.setPageSize(flights.getSize());
        flightResponse.setTotalElements(flights.getTotalElements());
        flightResponse.setTotalPages(flights.getTotalPages());
        flightResponse.setLast(flights.isLast());
        return flightResponse;

    }

    @Override
    public List<FlightDto> getFlightsByAircraftId(Long aircraftId) {
       List<Flight> flights =flightRepository.findByAircraftAircraftId(aircraftId);
           return flights.stream().map(flight -> mapToDto(flight)).collect(Collectors.toList());

    }

    @Override
    public List<FlightDto> getFlightsByDepartureAirport(Long departureAirport) {
        List<Flight> flights =flightRepository.findByDepartureAirportAirportId(departureAirport);
        return flights.stream().map(flight -> mapToDto(flight)).collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> getFlightsByDestinationAirport(Long destinationAirport) {
        List<Flight> flights =flightRepository.findByDestinationAirportAirportId(destinationAirport);
        return flights.stream().map(flight -> mapToDto(flight)).collect(Collectors.toList());
    }

    @Override
    public FlightDto getFlightByIdAircraftId(Long flightId, Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElseThrow(() -> new AircraftNotFoundException("Aircraft with associated Flight not found"));
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new FlightNotFoundException("Flight with associated Aircraft not found"));
        if (!(flight.getAircraft().getAircraftId()).equals (aircraft.getAircraftId())) {
            throw new FlightNotFoundException("This Flight does not belond to a aircraft");

        }

        return mapToDto(flight);
    }

    @Override
    public FlightDto getFlightByIdDepartureAirport(Long flightId, Long departureAirport) {
        Airport airport = airportRepository.findById(departureAirport).orElseThrow(() -> new AirportNotFoundException("Departure Airport with associated Flight not found"));
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new FlightNotFoundException("Flight with associated departure Airport not found"));
        if (!(flight.getDepartureAirport().getAirportId()).equals(airport.getAirportId())){
            throw new FlightNotFoundException("This Flight does not belond to a departure airport");

        }
        return mapToDto(flight);
    }

    @Override
    public FlightDto getFlightByIdDestinationAirport(Long flightId, Long destinationAirport) {
        Airport airport = airportRepository.findById(destinationAirport).orElseThrow(() -> new AirportNotFoundException("destination Airport with associated Flight not found"));
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new FlightNotFoundException("Flight with associated destination Airport not found"));
        if (! (flight.getDestinationAirport().getAirportId()).equals(airport.getAirportId()) ) {
            throw new FlightNotFoundException("This Flight does not belond to a departure airport");

        }

        return mapToDto(flight);
    }

    @Override
    public FlightDto createFlight(Long aircraftId, Long departureAirportId, Long destinationAirportId, FlightDto flightDto) {
        Flight flight= mapToEntity(flightDto);

        Aircraft aircraft=aircraftRepository.findById(aircraftId).orElseThrow(()-> new AircraftNotFoundException("Aircraft with associated Flight not found"));

        Airport departureAirport=airportRepository.findById(departureAirportId).orElseThrow(()-> new AirportNotFoundException(" departure Airport with associated Flight not found"));

        Airport destinationAirport=airportRepository.findById(destinationAirportId).orElseThrow(()-> new AirportNotFoundException("destination Airport with associated Flight not found"));
        flight.setAircraft(aircraft);
        flight.setDepartureAirport(departureAirport);
        flight.setDestinationAirport(destinationAirport);
        Flight newFlight = flightRepository.save(flight);
        return mapToDto(newFlight);

    }


    @Override
    public FlightDto updateFlight(Long aircraftId, Long departureAirportId, Long destinationAirportId,Long flightId ,FlightDto flightDto) {

        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new FlightNotFoundException("Flight with associated departure Airport not found"));
        Aircraft aircraft=aircraftRepository.findById(aircraftId).orElseThrow(()-> new AircraftNotFoundException("Aircraft with associated Flight not found"));

        Airport departureAirport=airportRepository.findById(departureAirportId).orElseThrow(()-> new AirportNotFoundException(" departure Airport with associated Flight not found"));

        Airport destinationAirport=airportRepository.findById(destinationAirportId).orElseThrow(()-> new AirportNotFoundException("destination Airport with associated Flight not found"));
        if (!((flight.getAircraft().getAircraftId()).equals (aircraft.getAircraftId())) ||
                (!(flight.getDepartureAirport().getAirportId()).equals(departureAirport.getAirportId()))||
                (!(flight.getDestinationAirport().getAirportId()).equals(destinationAirport.getAirportId()))) {

            throw new FlightNotFoundException("This Flight does not belond to a departure airport");
        }

        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setDepartureDate(flightDto.getDepartureDate());
        flight.setArrivalDate(flightDto.getArrivalDate());
        flight.setEconomyPrice(flightDto.getEconomyPrice());
        flight.setBusinessPrice(flightDto.getBusinessPrice());

        Flight updateFlight =flightRepository.save(flight);
        return mapToDto(updateFlight);
    }

    @Override
    public void deleteFlight(Long aircraftId, Long departureAirportId, Long destinationAirportId, Long flightId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new FlightNotFoundException("Flight with associated Id not found"));
        Aircraft aircraft=aircraftRepository.findById(aircraftId).orElseThrow(()-> new AircraftNotFoundException("Aircraft with associated Flight not found"));

        Airport departureAirport=airportRepository.findById(departureAirportId).orElseThrow(()-> new AirportNotFoundException(" departure Airport with associated Flight not found"));

        Airport destinationAirport=airportRepository.findById(destinationAirportId).orElseThrow(()-> new AirportNotFoundException("destination Airport with associated Flight not found"));

        if (!((flight.getAircraft().getAircraftId()).equals (aircraft.getAircraftId())) ||
                (!(flight.getDepartureAirport().getAirportId()).equals(departureAirport.getAirportId()))||
                (!(flight.getDestinationAirport().getAirportId()).equals(destinationAirport.getAirportId()))) {
            throw new FlightNotFoundException("This Flight does not belond to a departure airport");
        }
        flightRepository.delete(flight);

        }

    private FlightDto mapToDto(Flight flight){
        FlightDto flightDto = new FlightDto();

        flightDto.setFlightId(flight.getFlightId());
        flightDto.setFlightNumber(flight.getFlightNumber());
        flightDto.setDepartureDate(flight.getDepartureDate());
        flightDto.setArrivalDate(flight.getArrivalDate());
        flightDto.setEconomyPrice(flight.getEconomyPrice());
        flightDto.setBusinessPrice(flight.getBusinessPrice());

        return   flightDto;

    }

    private Flight mapToEntity(FlightDto flightDto){
        Flight flight = new Flight();

        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setDepartureDate(flightDto.getDepartureDate());
        flight.setArrivalDate(flightDto.getArrivalDate());
        flight.setEconomyPrice(flightDto.getEconomyPrice());
        flight.setBusinessPrice(flightDto.getBusinessPrice());
        return flight;
    }
}
