package com.flight.booking.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder
@Entity
@Table(name="Aircraft")

public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long aircraftId;

    private String manufacturer;
    private String model;
    private Integer passengerCapacity;

    @OneToMany(mappedBy="aircraft",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Flight> flights = new ArrayList<Flight>();


}
