package com.flight.booking.service;

import com.flight.booking.dto.PaymentDto;
import com.flight.booking.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(Long userId ,Long ticketId,ReservationDto reservationDto);
    List<ReservationDto> getReservationsByUser(Long userId);
    ReservationDto getReservationByTicket(Long reservationId,Long ticketId);

    ReservationDto updateReservation(Long userId ,Long ticketId,Long reservationId,ReservationDto reservationDto);
    void deleteReservation(Long userId ,Long ticketId,Long reservationId);

}
