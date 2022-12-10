package com.flight.booking.controller;


import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.FlightResponse;
import com.flight.booking.dto.TicketResponse;
import com.flight.booking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class FlightController {
    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @PostMapping("aircraft/{aircraftId}/departureAirport/{departureAirportId}/destinationAirport/{destinationAirportId}/flight")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FlightDto> createFlight(@PathVariable(value = "aircraftId") Long aircraftId,
                                                  @PathVariable(value = "departureAirportId") Long departureAirportId,
                                                  @PathVariable(value = "destinationAirportId") Long destinationAirportId,
                                                  @RequestBody FlightDto flightDto) {
        return new ResponseEntity<>(flightService.createFlight(aircraftId, departureAirportId, destinationAirportId, flightDto), HttpStatus.CREATED);
    }

    @GetMapping("flight/all")
    public ResponseEntity<FlightResponse> getFlights(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {

        return new ResponseEntity(flightService.getAllFlights(pageNo, pageSize), HttpStatus.OK);

    }

    @GetMapping("aircraft/{aircraftId}/flight")
    public List<FlightDto> getFlightsByAircraftId(@PathVariable(value = "aircraftId") Long aircraftId) {
        return flightService.getFlightsByAircraftId(aircraftId);
    }

    @GetMapping("departureAirport/{departureAirportId}/flight")
    public List<FlightDto> getFlightsByDepartureAirportId(@PathVariable(value = "departureAirportId") Long DepartureAirportId) {
        return flightService.getFlightsByDepartureAirport(DepartureAirportId);
    }

    @GetMapping("destinationAirport/{destinationAirportId}/flight")
    public List<FlightDto> getFlightsByDestinationAirportId(@PathVariable(value = "destinationAirportId") Long destinationAirportId) {
        return flightService.getFlightsByDestinationAirport(destinationAirportId);
    }

    @GetMapping("aircraft/{aircraftId}/flight/{flightId}")
    public ResponseEntity<FlightDto> getFlightByIdAircraftId(@PathVariable(value = "aircraftId") Long aircraftId, @PathVariable(value = "flightId") Long flightId) {
        FlightDto flightDto = flightService.getFlightByIdAircraftId(flightId, aircraftId);
        return new ResponseEntity<>(flightDto, HttpStatus.OK);
    }

    @GetMapping("departureAirport/{departureAirportId}/flight/{flightId}")
    public ResponseEntity<FlightDto> getFlightByIdDepartureAirport(@PathVariable(value = "departureAirportId") Long departureAirportId, @PathVariable(value = "flightId") Long flightId) {
        FlightDto flightDto = flightService.getFlightByIdDepartureAirport(flightId, departureAirportId);
        return new ResponseEntity<>(flightDto, HttpStatus.OK);
    }

    @GetMapping("destinationAirport/{destinationAirportId}/flight/{flightId}")
    public ResponseEntity<FlightDto> getFlightByIdDestinationAirport(@PathVariable(value = "destinationAirportId") Long destinationAirportId, @PathVariable(value = "flightId") Long flightId) {
        FlightDto flightDto = flightService.getFlightByIdDestinationAirport(flightId, destinationAirportId);
        return new ResponseEntity<>(flightDto, HttpStatus.OK);
    }

    @PutMapping("aircraft/{aircraftId}/departureAirport/{departureAirportId}/destinationAirport/{destinationAirportId}/flight/{flightId}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable(value = "aircraftId") Long aircraftId,
                                                  @PathVariable(value = "departureAirportId") Long departureAirportId,
                                                  @PathVariable(value = "destinationAirportId") Long destinationAirportId,
                                                  @PathVariable(value = "flightId") Long flightId,
                                                  @RequestBody FlightDto flightDto) {
        FlightDto updateFlight=flightService.updateFlight(aircraftId,departureAirportId,destinationAirportId,flightId,flightDto);
        return new ResponseEntity<>(updateFlight,HttpStatus.OK);


    }
    @DeleteMapping("aircraft/{aircraftId}/departureAirport/{departureAirportId}/destinationAirport/{destinationAirportId}/flight/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable(value = "aircraftId") Long aircraftId,
                                                  @PathVariable(value = "departureAirportId") Long departureAirportId,
                                                  @PathVariable(value = "destinationAirportId") Long destinationAirportId,
                                                  @PathVariable(value = "flightId") Long flightId){
         flightService.deleteFlight(aircraftId,departureAirportId,destinationAirportId,flightId);
        return new ResponseEntity<>("Flight deleted successfully",HttpStatus.OK);
    }
}