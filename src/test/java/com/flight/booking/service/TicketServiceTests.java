package com.flight.booking.service;

import com.flight.booking.dto.TicketDto;
import com.flight.booking.dto.TicketResponse;
import com.flight.booking.model.Ticket;
import com.flight.booking.repository.TicketRepository;
import com.flight.booking.service.Impl.TicketServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTests {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    public void TicketService_CreateTicket_ReturnsTicketDto() {
        Ticket ticket = Ticket.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();
        TicketDto ticketDto = TicketDto.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();

        when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);
        TicketDto savedTicket = ticketService.createTicket(ticketDto);

        Assertions.assertThat(savedTicket).isNotNull();
    }

    @Test
    public void TicketService_GetAllTicket_ReturnsResponseDto() {
        Page<Ticket> tickets = Mockito.mock(Page.class);

        when(ticketRepository.findAll(Mockito.any(Pageable.class))).thenReturn(tickets);

        TicketResponse saveTicket = ticketService.getAllTickets(1, 10);
        Assertions.assertThat(saveTicket).isNotNull();
    }

    @Test
    public void TicketService_FindById_ReturnTicketDto() {
        Long ticketId = 1L;
        Ticket ticket = Ticket.builder()
                .tickedId(1L)
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.ofNullable(ticket));

        TicketDto ticketReturn = ticketService.getTicketById(ticketId);
        Assertions.assertThat(ticketReturn).isNotNull();
    }

    @Test
    public void TicketService_UpdateTicket_ReturnTicketDto() {
        Long ticketId = 1L;
        Ticket ticket = Ticket.builder()
                .tickedId(1L)
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();
        TicketDto ticketDto = TicketDto.builder()
                .tickedId(1L)
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();


        when(ticketRepository.findById(ticketId)).thenReturn(Optional.ofNullable(ticket));
       when(ticketRepository.save(ticket)).thenReturn(ticket);
       TicketDto updateTicket = ticketService.updateTicket(ticketDto,ticketId);

       Assertions.assertThat(updateTicket).isNotNull();

    }

    @Test
    public void TicketService_DeleteTicketById_ReturnVoid(){
        Long ticketId = 1L;
        Ticket ticket = Ticket.builder()
                .tickedId(1L)
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();


        when(ticketRepository.findById(ticketId)).thenReturn(Optional.ofNullable(ticket));
       doNothing().when(ticketRepository).delete(ticket);

       assertAll(()->ticketService.deleteTicketId(ticketId));
    }
}