package com.flight.booking.repository;


import com.flight.booking.model.Airport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AirportRepositoryTests {
    private AirportRepository airportRepository;

    @Autowired
    public AirportRepositoryTests(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
    @Test
    public void AirportRepository_saveAll_ReturnSavedAirport(){

        Airport airport = Airport.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();


        Airport savedAirport = airportRepository.save(airport);


        Assertions.assertThat(savedAirport).isNotNull();
        Assertions.assertThat(savedAirport.getAirportId()).isGreaterThan(0);

    }
    @Test
    public void AirportRepository_GetAll_ReturnMoreThenOneAirport(){
        Airport airport = Airport.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();
        Airport airport1 = Airport.builder()
                .airportCode("Code1")
                .airportName("Name1")
                .city("city1")
                .state("state1")
                .country("country1").build();

        airportRepository.save(airport);
        airportRepository.save(airport1);

        List<Airport> airportList = airportRepository.findAll();

        Assertions.assertThat(airportList).isNotNull();
        Assertions.assertThat(airportList.size()).isEqualTo(2);

    }
    @Test
    public void AirportRepository_findById_ReturnAirport(){
        Airport airport = Airport.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();

        airportRepository.save(airport);


        Airport airport1 = airportRepository.findById(airport.getAirportId()).get();

        Assertions.assertThat(airport1).isNotNull();


    }
    @Test
    public void AirportRepository_updateAirport_ReturnAirportNOtNull(){
        Airport airport = Airport.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();

        airportRepository.save(airport);


        Airport airportSave = airportRepository.findById(airport.getAirportId()).get();
        airportSave.setAirportCode("Code3");
        airportSave.setAirportName("Name3");
        airportSave.setCity("city3");
        airportSave.setState("state3");
        airportSave.setCity("city3");
        airportSave.setCountry("country3");
        Airport updatedAirport = airportRepository.save(airportSave);

        Assertions.assertThat(updatedAirport.getAirportCode()).isNotNull();
        Assertions.assertThat(updatedAirport.getAirportName()).isNotNull();
        Assertions.assertThat(updatedAirport.getCity()).isNotNull();
        Assertions.assertThat(updatedAirport.getState()).isNotNull();
        Assertions.assertThat(updatedAirport.getCountry()).isNotNull();
    }
    @Test
    public void AirportRepository_deleteAirport_ReturnAirportNOtNull(){
        Airport airport = Airport.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();

        airportRepository.save(airport);


        airportRepository.deleteById( airport.getAirportId());
        Optional<Airport> airportReturn =  airportRepository.findById( airport.getAirportId());


        Assertions.assertThat(airportReturn).isEmpty();


    }

}
