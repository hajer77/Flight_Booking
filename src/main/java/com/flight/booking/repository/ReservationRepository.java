package com.flight.booking.repository;

import com.flight.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByUserUserId(Long userId);
}
