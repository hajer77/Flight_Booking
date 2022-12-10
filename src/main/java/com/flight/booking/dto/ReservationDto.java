package com.flight.booking.dto;

import com.flight.booking.model.Ticket;
import com.flight.booking.model.UserEntity;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ReservationDto {
    private Long reservationId;
    private String reservationNumber;
    private LocalDate reservationDate;


}

