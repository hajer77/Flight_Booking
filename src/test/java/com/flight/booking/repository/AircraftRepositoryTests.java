package com.flight.booking.repository;

import com.flight.booking.model.Aircraft;
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
public class AircraftRepositoryTests {
    private  AircraftRepository aircraftRepository ;

    @Autowired
    public AircraftRepositoryTests(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }
    @Test
    public void AircraftRepository_saveAll_ReturnSavedAircraft(){

        Aircraft aircraft = Aircraft.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();


        Aircraft savedAircraft = aircraftRepository.save(aircraft);


        Assertions.assertThat(savedAircraft).isNotNull();
        Assertions.assertThat(savedAircraft.getAircraftId()).isGreaterThan(0);

    }
    @Test
    public void AircraftRepository_GetAll_ReturnMoreThenOneAircraft(){
        Aircraft aircraft = Aircraft.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();
        Aircraft aircraft1 = Aircraft.builder()
                .model("model2")
                .manufacturer("manufacturer2")
                .passengerCapacity(350).build();
        aircraftRepository.save(aircraft);
        aircraftRepository.save(aircraft1);

        List<Aircraft> aircraftList = aircraftRepository.findAll();

        Assertions.assertThat(aircraftList).isNotNull();
        Assertions.assertThat(aircraftList.size()).isEqualTo(2);

    }
    @Test
    public void AircraftRepository_findById_ReturnAircraft(){
        Aircraft aircraft = Aircraft.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();

        aircraftRepository.save(aircraft);


        Aircraft aircraft1 = aircraftRepository.findById(aircraft.getAircraftId()).get();

        Assertions.assertThat(aircraft1).isNotNull();


    }
    @Test
    public void AircraftRepository_updateAircraft_ReturnAircraftNOtNull(){
        Aircraft aircraft = Aircraft.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();

        aircraftRepository.save(aircraft);


        Aircraft aircraftSave = aircraftRepository.findById(aircraft.getAircraftId()).get();
        aircraftSave.setModel("model12");
        aircraftSave.setManufacturer("manufacturer12");
        aircraftSave.setPassengerCapacity(300);

        Aircraft updatedAircraft = aircraftRepository.save(aircraftSave);

        Assertions.assertThat(updatedAircraft.getModel()).isNotNull();
        Assertions.assertThat(updatedAircraft.getManufacturer()).isNotNull();
        Assertions.assertThat(updatedAircraft.getPassengerCapacity()).isNotNull();


    }
    @Test
    public void AircraftRepository_deleteAircraft_ReturnAircraftNOtNull(){
        Aircraft aircraft = Aircraft.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();

        aircraftRepository.save(aircraft);


        aircraftRepository.deleteById( aircraft.getAircraftId());
        Optional<Aircraft> aircraftReturn =  aircraftRepository.findById( aircraft.getAircraftId());


        Assertions.assertThat(aircraftReturn).isEmpty();


    }

}
