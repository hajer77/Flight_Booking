package com.flight.booking.repository;

import com.flight.booking.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByUserUserId(Long userId);

}
