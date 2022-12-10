package com.flight.booking.service.Impl;

import com.flight.booking.dto.PaymentDto;
import com.flight.booking.exceptions.PaymentNotFoundException;
import com.flight.booking.exceptions.TicketNotFoundException;
import com.flight.booking.exceptions.UserNotFoundException;
import com.flight.booking.model.Payment;
import com.flight.booking.model.Reservation;
import com.flight.booking.model.Ticket;
import com.flight.booking.model.UserEntity;
import com.flight.booking.repository.PaymentRepository;
import com.flight.booking.repository.TicketRepository;
import com.flight.booking.repository.UserRepository;
import com.flight.booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {


    private PaymentRepository paymentRepository;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;


    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<PaymentDto> getPaymentsByUser(Long userId) {
        List<Payment> payments =paymentRepository.findByUserUserId(userId);

        return payments.stream().map(payment -> mapToDto(payment)).collect(Collectors.toList());
    }


    @Override
    public PaymentDto getPaymentByTicket(Long paymentId ,Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()-> new TicketNotFoundException("Ticket with associated payment not found"));
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new PaymentNotFoundException("Payment with associated not found"));
        if(!(payment.getTicket().getTickedId()).equals(ticket.getTickedId())){
            throw new PaymentNotFoundException("Payment not found ");
        }
        return mapToDto(payment);
    }

    @Override
    public PaymentDto createPayment(Long userId,Long ticketId,PaymentDto paymentDto) {
        Payment payment=mapToEntity(paymentDto);

        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with associated payment not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("Ticket with associated payment not found"));

        payment.setUser(user);
        payment.setTicket(ticket);

        Payment newPayment =paymentRepository.save(payment);

        return mapToDto(newPayment);

    }

    @Override
    public PaymentDto updatePayment(Long userId, Long ticketId, Long paymentId, PaymentDto paymentDto) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new PaymentNotFoundException("Payment with associated not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with associated payment not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("Ticket with associated payment not found"));
        if(!((payment.getTicket().getTickedId()).equals(ticket.getTickedId())) ||
                (!((payment.getUser().getUserId()).equals(user.getUserId())))){
            throw new PaymentNotFoundException("Payment not found ");
        }

        payment.setPaymentAmount(paymentDto.getPaymentAmount());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setCreditCardId(paymentDto.getCreditCardId());

        Payment updatePayment = paymentRepository.save(payment);

        return mapToDto(updatePayment);
    }

    @Override
    public void deletePayment(Long userId, Long ticketId, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new PaymentNotFoundException("Payment with associated not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with associated payment not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("Ticket with associated payment not found"));

        if(!((payment.getTicket().getTickedId()).equals(ticket.getTickedId())) ||
                (!((payment.getUser().getUserId()).equals(user.getUserId())))){
            throw new PaymentNotFoundException("Payment not found ");
        }

        paymentRepository.delete(payment);

    }

    private PaymentDto mapToDto(Payment payment){
        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setPaymentId(payment.getPaymentId());
        paymentDto.setPaymentDate(payment.getPaymentDate());
        paymentDto.setPaymentAmount(payment.getPaymentAmount());
        paymentDto.setCreditCardId(payment.getCreditCardId());

        return paymentDto;
    }
    private Payment mapToEntity(PaymentDto paymentDto){
        Payment payment= new Payment();

        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setPaymentAmount(paymentDto.getPaymentAmount());
        payment.setCreditCardId(paymentDto.getCreditCardId());

        return payment;
    }

}
