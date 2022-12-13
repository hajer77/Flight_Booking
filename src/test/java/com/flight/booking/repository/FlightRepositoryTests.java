package com.flight.booking.repository;


import com.flight.booking.model.Flight;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FlightRepositoryTests {


    private FlightRepository flightRepository;


    @Autowired
    public FlightRepositoryTests(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    @Test
    public void FlightRepository_saveAll_ReturnSavedFlight(){


        Flight flight = Flight.builder()
                .flightNumber("FNumber")
                .economyPrice(200.00)
                .businessPrice(400.00)
                .departureDate(LocalDate.parse("2022-12-20"))
                .arrivalDate(LocalDate.parse("2022-12-20")).build();

        Flight savedFlight = flightRepository.save(flight);

        Assertions.assertThat(savedFlight).isNotNull();
        Assertions.assertThat(savedFlight.getFlightId()).isGreaterThan(0);


    }

    @Test
    public void Repository_findById_ReturnFlight(){
        Flight flight = Flight.builder()
                .flightNumber("FNumber")
                .economyPrice(200.00)
                .businessPrice(400.00)
                .departureDate(LocalDate.parse("2022-12-20"))
                .arrivalDate(LocalDate.parse("2022-12-20")).build();

        flightRepository.save(flight);


        Flight flight1 = flightRepository.findById(flight.getFlightId()).get();

        Assertions.assertThat(flight1).isNotNull();


    }
    @Test
    public void FlightRepository_updateFlight_ReturnFlightNOtNull(){
        Flight flight = Flight.builder()
                .flightNumber("FNumber")
                .economyPrice(200.00)
                .businessPrice(400.00)
                .departureDate(LocalDate.parse("2022-12-20"))
                .arrivalDate(LocalDate.parse("2022-12-20")).build();

        flightRepository.save(flight);


        Flight flightSave = flightRepository.findById(flight.getFlightId()).get();
        flightSave.setFlightNumber("FNumber");
        flightSave.setEconomyPrice(200.00);
        flightSave.setBusinessPrice(400.00);
        flightSave.setDepartureDate(LocalDate.parse("2022-12-20"));
        flightSave.setArrivalDate(LocalDate.parse("2022-12-20"));
        Flight updatedFlight = flightRepository.save(flightSave);

        Assertions.assertThat(updatedFlight.getFlightNumber()).isNotNull();
        Assertions.assertThat(updatedFlight.getEconomyPrice()).isNotNull();
        Assertions.assertThat(updatedFlight.getBusinessPrice()).isNotNull();
        Assertions.assertThat(updatedFlight.getDepartureDate()).isNotNull();
        Assertions.assertThat(updatedFlight.getArrivalDate()).isNotNull();





    }

@Test
public void FlightRepository_deleteFlight_ReturnVoid() {
    Flight flight = Flight.builder()
            .flightNumber("FNumber")
            .economyPrice(200.00)
            .businessPrice(400.00)
            .departureDate(LocalDate.parse("2022-12-20"))
            .arrivalDate(LocalDate.parse("2022-12-20")).build();

    flightRepository.save(flight);


    flightRepository.deleteById(flight.getFlightId());
    Optional<Flight> flightReturn = flightRepository.findById(flight.getFlightId());
    Assertions.assertThat(flightReturn).isEmpty();

}
}
