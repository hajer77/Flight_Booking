package com.flight.booking.repository;


import com.flight.booking.model.Ticket;

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
public class TicketRepositoryTests {


    private TicketRepository ticketRepository;

    @Autowired
    public TicketRepositoryTests(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Test
    public void TicketRepository_saveAll_ReturnSavedTicket(){


        Ticket ticket = Ticket.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();

                System.out.println(ticket.getTypeOfTicket());


         Ticket savedTicket = ticketRepository.save(ticket);


         Assertions.assertThat(savedTicket).isNotNull();
         Assertions.assertThat(savedTicket.getTickedId()).isGreaterThan(0);


     }
     @Test
    public void TicketRepository_GetAll_ReturnMoreThenOneTicket(){
         Ticket ticket = Ticket.builder()
                 .typeOfTicket("economy")
                 .price(200.00)
                 .maxKg(20.00)
                 .seatNumber("5AAAB").build();
         Ticket ticket1 = Ticket.builder()
                 .typeOfTicket("economy")
                 .price(200.00)
                 .maxKg(20.00)
                 .seatNumber("AB8596").build();
         ticketRepository.save(ticket);
         ticketRepository.save(ticket1);

         List<Ticket> ticketList = ticketRepository.findAll();

         Assertions.assertThat(ticketList).isNotNull();
         Assertions.assertThat(ticketList.size()).isEqualTo(2);

     }
    @Test
    public void TicketRepository_findById_ReturnTicket(){
        Ticket ticket = Ticket.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();

        ticketRepository.save(ticket);


        Ticket ticket1 = ticketRepository.findById(ticket.getTickedId()).get();

        Assertions.assertThat(ticket1).isNotNull();


    }
    @Test
    public void TicketRepository_findBySeatNumber_ReturnTicketNotNull(){
        Ticket ticket = Ticket.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();

        ticketRepository.save(ticket);


        Ticket ticket1 = ticketRepository.findBySeatNumber(ticket.getSeatNumber()).get();

        Assertions.assertThat(ticket1).isNotNull();


    }

    @Test
    public void TicketRepository_updateTicket_ReturnTicketNOtNull(){
        Ticket ticket = Ticket.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();

        ticketRepository.save(ticket);


        Ticket ticketSave = ticketRepository.findById(ticket.getTickedId()).get();
        ticketSave.setTypeOfTicket("typeTicket");
        ticketSave.setPrice(120.00);
        ticketSave.setMaxKg(30.00);
        ticketSave.setSeatNumber("7895L");
        Ticket updatedTicket = ticketRepository.save(ticketSave);

        Assertions.assertThat(updatedTicket.getTypeOfTicket()).isNotNull();
        Assertions.assertThat(updatedTicket.getPrice()).isNotNull();
        Assertions.assertThat(updatedTicket.getMaxKg()).isNotNull();
        Assertions.assertThat(updatedTicket.getSeatNumber()).isNotNull();


    }
    @Test
    public void TicketRepository_deleteTicket_ReturnTicketNOtNull(){
        Ticket ticket = Ticket.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();

        ticketRepository.save(ticket);


        ticketRepository.deleteById(ticket.getTickedId());
        Optional<Ticket> ticketReturn = ticketRepository.findById(ticket.getTickedId());


        Assertions.assertThat(ticketReturn).isEmpty();


    }

}
