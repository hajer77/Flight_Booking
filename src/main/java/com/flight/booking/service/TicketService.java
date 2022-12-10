package com.flight.booking.service;

import com.flight.booking.dto.TicketDto;
import com.flight.booking.dto.TicketResponse;

import java.util.List;

public interface TicketService {
    TicketDto createTicket(TicketDto ticketDto);
    TicketResponse getAllTickets(int pageNo,int pageSize);
    TicketDto getTicketById(Long ticketId);
    TicketDto updateTicket(TicketDto ticketDto,Long ticketId);
    void deleteTicketId(Long ticketId);
}
