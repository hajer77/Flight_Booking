package com.flight.booking.controller;


import com.flight.booking.dto.FlightDto;
import com.flight.booking.dto.PaymentDto;
import com.flight.booking.dto.ReservationDto;
import com.flight.booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class PaymentController {
    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("user/{userId}/ticket/{ticketId}/payment/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentDto> createPayment(@PathVariable (value ="userId") Long userId,
                                                    @PathVariable(value="ticketId") Long ticketId,
                                                    @RequestBody PaymentDto paymentDto){
        return new ResponseEntity<>(paymentService.createPayment(userId,ticketId,paymentDto),HttpStatus.CREATED);
    }

    @GetMapping("user/{userId}/payment")
    public List<PaymentDto> getPaymentsByUserId(@PathVariable(value = "userId") Long userId) {
        return paymentService.getPaymentsByUser(userId);


    }
    @GetMapping("ticket/{ticketId}/payment/{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentByTicket(@PathVariable(value = "paymentId") Long paymentId, @PathVariable(value = "ticketId") Long ticketId) {
        PaymentDto paymentDto = paymentService.getPaymentByTicket(paymentId,ticketId);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }
    @PutMapping("user/{userId}/ticket/{ticketId}/payment/{paymentId}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable(value = "userId") Long userId,
                                                  @PathVariable(value = "ticketId") Long ticketId,
                                                  @PathVariable(value = "paymentId") Long paymentId,
                                                  @RequestBody PaymentDto paymentDto) {
        PaymentDto updatePayment=paymentService.updatePayment(userId,ticketId,paymentId,paymentDto);
        return new ResponseEntity<>(updatePayment,HttpStatus.OK);


    }
    @DeleteMapping("user/{userId}/ticket/{ticketId}/payment/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable(value = "userId") Long userId,
                                                @PathVariable(value = "ticketId") Long ticketId,
                                                @PathVariable(value = "paymentId") Long paymentId) {
        paymentService.deletePayment(userId,ticketId,paymentId);
        return new ResponseEntity<>("Payment delete successfully",HttpStatus.OK);


    }

}
