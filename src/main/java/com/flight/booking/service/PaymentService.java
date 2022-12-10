package com.flight.booking.service;

import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto createPayment(Long userId,Long ticketId,PaymentDto paymentDto);
    List<PaymentDto> getPaymentsByUser(Long userId);
    PaymentDto getPaymentByTicket(Long paymentId,Long ticketId);

    PaymentDto updatePayment(Long userId,Long ticketId,Long paymentId,PaymentDto paymentDto);

    void deletePayment(Long userId,Long ticketId,Long paymentId);

}
