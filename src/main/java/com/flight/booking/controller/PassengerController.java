package com.flight.booking.controller;


import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.PassengerDto;
import com.flight.booking.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class PassengerController {
    private PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }


    @PostMapping("ticket/{ticketId}/flight/{flightId}/passenger")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PassengerDto> createPassenger( @PathVariable("ticketId") Long ticketId,
                                                         @PathVariable("flightId") Long flightId,
                                                         @RequestBody PassengerDto passengerDto){
        return new ResponseEntity<>(passengerService.createPassenger(ticketId,flightId, passengerDto),HttpStatus.CREATED);
    }
    @GetMapping("flight/{flightId}/passenger")
    public List<PassengerDto> getPassengersByFlightId(@PathVariable(value = "flightId") Long flightId) {
        return passengerService.getPassengersByFlightId(flightId);
    }
    @GetMapping("ticket/{ticketId}/passenger/{passengerId}")
    public ResponseEntity<PassengerDto> getPassengerById(@PathVariable(value = "passengerId") Long passengerId, @PathVariable(value = "ticketId") Long ticketId) {
        PassengerDto passengerDto = passengerService.getPassengerById(passengerId, ticketId);
        return new ResponseEntity<>(passengerDto, HttpStatus.OK);
    }
    @PutMapping("ticket/{ticketId}/flight/{flightId}/passenger/{passengerId}")
    public ResponseEntity<PassengerDto> updatePassenger(@PathVariable(value="passengerId") Long passengerId,
                                                        @PathVariable(value="ticketId")Long ticketId,
                                                        @PathVariable(value="flightId")Long flightId,
                                                        @RequestBody PassengerDto passengerDto){
        PassengerDto updatePassenger = passengerService.updatePassenger(ticketId,flightId,passengerId,passengerDto);
        return new ResponseEntity<>(updatePassenger,HttpStatus.OK);
    }
    @DeleteMapping("ticket/{ticketId}/flight/{flightId}/passenger/{passengerId}")
    public ResponseEntity<String> deletePassenger(@PathVariable(value="passengerId") Long passengerId,
                                                        @PathVariable(value="ticketId")Long ticketId,
                                                        @PathVariable(value="flightId")Long flightId){
         passengerService.deletePassenger(ticketId,flightId,passengerId);
        return new ResponseEntity<>("Passenger deleted successfully",HttpStatus.OK);
    }


}
