package com.flight.booking.repository;

import com.flight.booking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {



    Optional<Ticket> findBySeatNumber(String seatNumber);
}
