package com.flight.booking.repository;

import com.flight.booking.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  AircraftRepository extends JpaRepository<Aircraft,Long> {
}
