package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="Flight")

public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long flightId;

    private String flightNumber;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name ="departureAirport_id" , nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name ="destinationAirport_id" , nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Airport destinationAirport;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name ="aircraft_id" , nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Aircraft aircraft;

    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss")
    private LocalDate departureDate;

    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss")
    private LocalDate arrivalDate;


    private Double economyPrice;
    private Double businessPrice;

    @OneToMany(mappedBy="flight",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Passenger> passengers = new ArrayList<Passenger>();

}
