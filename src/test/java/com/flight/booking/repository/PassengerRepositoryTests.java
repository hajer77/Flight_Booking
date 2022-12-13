package com.flight.booking.repository;


import com.flight.booking.model.Flight;
import com.flight.booking.model.Passenger;
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
public class PassengerRepositoryTests {

    private PassengerRepository passengerRepository;

    @Autowired
    public PassengerRepositoryTests(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Test
    public void PassengerRepository_saveAll_ReturnSavedPassenger(){


        Passenger passenger = Passenger.builder()
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("+25693333333")
                .passportNumber("A4821566")
                .address("address")
                .email("email@gmail.com")
                .age(30).build();


        Passenger savedPassenger = passengerRepository.save(passenger);

        Assertions.assertThat(savedPassenger).isNotNull();
        Assertions.assertThat(savedPassenger.getPassengerId()).isGreaterThan(0);


    }
    @Test
    public void RepositoryPassenger_findById_ReturnFlightPassenger(){
        Passenger passenger = Passenger.builder()
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("+25693333333")
                .passportNumber("A4821566")
                .address("address")
                .email("email@gmail.com")
                .age(30).build();

        passengerRepository.save(passenger);


        Passenger passenger1 = passengerRepository.findById(passenger.getPassengerId()).get();

        Assertions.assertThat(passenger1).isNotNull();


    }
    @Test
    public void PassengerRepository_updatePassenger_ReturnPassengerNOtNull(){
        Passenger passenger = Passenger.builder()
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("+25693333333")
                .passportNumber("A4821566")
                .address("address")
                .email("email@gmail.com")
                .age(30).build();

        passengerRepository.save(passenger);


        Passenger passengerSave = passengerRepository.findById(passenger.getPassengerId()).get();
        passengerSave.setFirstName("firstName");
        passengerSave.setLastName("lastName");
        passengerSave.setPhoneNumber("+25693333333");
        passengerSave.setAddress("address");
        passengerSave.setEmail("email@gmail.com");
        passengerSave.setAge(30);
        Passenger updatedPassenger = passengerRepository.save(passengerSave);

        Assertions.assertThat(updatedPassenger.getFirstName()).isNotNull();
        Assertions.assertThat(updatedPassenger.getLastName()).isNotNull();
        Assertions.assertThat(updatedPassenger.getPhoneNumber()).isNotNull();
        Assertions.assertThat(updatedPassenger.getAddress()).isNotNull();
        Assertions.assertThat(updatedPassenger.getEmail()).isNotNull();
        Assertions.assertThat(updatedPassenger.getAge()).isNotNull();

    }
    @Test
    public void PassengerRepository_deletePassenger_ReturnVoid() {
        Passenger passenger = Passenger.builder()
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("+25693333333")
                .passportNumber("A4821566")
                .address("address")
                .email("email@gmail.com")
                .age(30).build();

        passengerRepository.save(passenger);


        passengerRepository.deleteById(passenger.getPassengerId());
        Optional<Passenger> passengerReturn = passengerRepository.findById(passenger.getPassengerId());
        Assertions.assertThat(passengerReturn).isEmpty();

    }
}
