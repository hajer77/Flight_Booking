package com.flight.booking.dto;

import com.flight.booking.model.Flight;
import com.flight.booking.model.Ticket;
import com.flight.booking.model.UserEntity;
import lombok.Data;

import java.time.LocalDate;


@Data
public class PaymentDto {
    private Long paymentId;
    private Integer creditCardId;
    private LocalDate paymentDate;
    private Double paymentAmount;

}
