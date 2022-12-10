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
@Table(name="Airport")

public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airportId;

    private String airportCode;
    private String airportName;
    private String city;
    private String state;
    private String country;

    @OneToMany(mappedBy="departureAirport",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Flight> flightsDepartureAirport = new ArrayList<Flight>();
    @OneToMany(mappedBy="destinationAirport",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Flight> flightsDestinationAirport = new ArrayList<Flight>();



}
