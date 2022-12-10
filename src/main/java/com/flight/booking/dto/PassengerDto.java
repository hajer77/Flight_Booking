package com.flight.booking.dto;

import com.flight.booking.model.Flight;
import com.flight.booking.model.Ticket;
import lombok.Data;

@Data
public class PassengerDto {
    private Long passengerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String passportNumber;
    private Integer age;
    private String email;
    private String address;

}
