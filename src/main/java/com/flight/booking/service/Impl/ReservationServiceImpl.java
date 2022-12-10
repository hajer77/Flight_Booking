package com.flight.booking.service.Impl;

import com.flight.booking.dto.ReservationDto;
import com.flight.booking.exceptions.PaymentNotFoundException;
import com.flight.booking.exceptions.ReservationNotFoundException;
import com.flight.booking.exceptions.TicketNotFoundException;
import com.flight.booking.exceptions.UserNotFoundException;
import com.flight.booking.model.*;
import com.flight.booking.repository.ReservationRepository;
import com.flight.booking.repository.TicketRepository;
import com.flight.booking.repository.UserRepository;
import com.flight.booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    public List<ReservationDto> getReservationsByUser(Long userId) {
        List<Reservation> reservations =reservationRepository.findByUserUserId(userId);
        return reservations.stream().map(reservation -> mapToDto(reservation)).collect(Collectors.toList());
    }


    @Override
    public ReservationDto getReservationByTicket(Long reservationId, Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()-> new TicketNotFoundException("Ticket with associated payment not found"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new ReservationNotFoundException("Reservation with associated not found"));
        if(!(reservation.getTicket().getTickedId()).equals(ticket.getTickedId())){
            throw new ReservationNotFoundException("Reservation not found ");
        }
        return mapToDto(reservation);
    }

    @Override
    public ReservationDto createReservation(Long userId,Long ticketId,ReservationDto reservationDto) {
        Reservation reservation = mapToEntity(reservationDto);

        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with associated payment not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("Ticket with associated payment not found"));

        reservation.setUser(user);
        reservation.setTicket(ticket);

        Reservation newReservation =reservationRepository.save(reservation);

        return mapToDto(newReservation);



    }

    @Override
    public ReservationDto updateReservation(Long userId, Long ticketId, Long reservationId, ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new ReservationNotFoundException("Reservation  not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with associated reservation  not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("Ticket with associated reservation not found"));
        if(!((reservation.getTicket().getTickedId()).equals(ticket.getTickedId())) ||
                (!((reservation.getUser().getUserId()).equals(user.getUserId())))){
            throw new ReservationNotFoundException("Reservation not found ");
        }

        reservation.setReservationNumber(reservationDto.getReservationNumber());
        reservation.setReservationDate(reservationDto.getReservationDate());


        Reservation updateReservation = reservationRepository.save(reservation);

        return mapToDto(updateReservation);
    }

    @Override
    public void deleteReservation(Long userId, Long ticketId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new ReservationNotFoundException("Reservation  not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with associated reservation  not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("Ticket with associated reservation not found"));

        if(!((reservation.getTicket().getTickedId()).equals(ticket.getTickedId())) ||
                (!((reservation.getUser().getUserId()).equals(user.getUserId())))){
            throw new ReservationNotFoundException("Reservation not found ");
        }

       reservationRepository.delete(reservation);


    }

    private ReservationDto mapToDto(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setReservationId(reservation.getReservationId());
        reservationDto.setReservationDate(reservation.getReservationDate());
        reservationDto.setReservationNumber(reservation.getReservationNumber());

        return reservationDto;

    }
      private Reservation mapToEntity(ReservationDto reservationDto){
        Reservation reservation = new Reservation();

        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setReservationNumber(reservationDto.getReservationNumber());

        return reservation;
      }

}
