package com.flight.booking.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Ticket")

public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tickedId;


    private String seatNumber;
    private Double maxKg;
    private String typeOfTicket;
    private Double price;



}
