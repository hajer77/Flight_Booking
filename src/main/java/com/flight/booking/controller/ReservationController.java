package com.flight.booking.controller;


import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.PaymentDto;
import com.flight.booking.dto.ReservationDto;
import com.flight.booking.service.PaymentService;
import com.flight.booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }




    @PostMapping("user/{userId}/ticket/{ticketId}/reservation/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationDto> createReservation(@PathVariable(value ="userId") Long userId,
                                                            @PathVariable(value="ticketId") Long ticketId,
                                                            @RequestBody ReservationDto reservationDto){
        return new ResponseEntity<>(reservationService.createReservation(userId,ticketId,reservationDto),HttpStatus.CREATED);
    }

    @GetMapping("user/{userId}/reservation")
    public List<ReservationDto> getReservationsByUserId(@PathVariable(value = "userId") Long userId) {
        return reservationService.getReservationsByUser(userId);
    }

    @GetMapping("ticket/{ticketId}/reservation/{reservationId}")
    public ResponseEntity<ReservationDto> getReservationByTicket(@PathVariable(value = "reservationId") Long reservationId, @PathVariable(value = "ticketId") Long ticketId) {
        ReservationDto reservationDto = reservationService.getReservationByTicket(reservationId,ticketId);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }
    @PutMapping("user/{userId}/ticket/{ticketId}/reservation/{reservationId}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable(value = "userId") Long userId,
                                                    @PathVariable(value = "ticketId") Long ticketId,
                                                    @PathVariable(value = "reservationId") Long reservationId,
                                                    @RequestBody ReservationDto reservationDto) {
        ReservationDto updateReservation=reservationService.updateReservation(userId,ticketId,reservationId,reservationDto);
        return new ResponseEntity<>(updateReservation,HttpStatus.OK);


    }
    @DeleteMapping("user/{userId}/ticket/{ticketId}/reservation/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable(value = "userId") Long userId,
                                                    @PathVariable(value = "ticketId") Long ticketId,
                                                    @PathVariable(value = "reservationId") Long reservationId) {
        reservationService.deleteReservation(userId,ticketId,reservationId);
        return new ResponseEntity<>("Reservation deleted successfully",HttpStatus.OK);


    }
}
