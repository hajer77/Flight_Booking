package com.flight.booking.service.Impl;


import com.flight.booking.dto.TicketDto;
import com.flight.booking.dto.TicketResponse;
import com.flight.booking.exceptions.TicketNotFoundException;
import com.flight.booking.model.Ticket;
import com.flight.booking.repository.TicketRepository;
import com.flight.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }



    @Override
    public TicketDto createTicket(TicketDto ticketDto) {
       Ticket ticket = mapToEntity(ticketDto) ;
       Ticket newTicket = ticketRepository.save(ticket);
       return mapToDto(newTicket);

    }

    @Override
    public TicketResponse getAllTickets(int pageNo , int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Ticket> tickets =ticketRepository.findAll(pageable);
        List<Ticket> ListOfTicket = tickets.getContent();
        List<TicketDto> content = ListOfTicket.stream().map(ticket1 ->mapToDto(ticket1) ).collect(Collectors.toList());

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setContent(content);
        ticketResponse.setPageNo(tickets.getNumber());
        ticketResponse.setPageSize(tickets.getSize());
        ticketResponse.setTotalElements(tickets.getTotalElements());
        ticketResponse.setTotalPages(tickets.getTotalPages());
        ticketResponse.setLast(tickets.isLast());
        return ticketResponse;

    }

    @Override
    public TicketDto getTicketById(Long ticketId) {
        Ticket ticket=ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket could not be found"));
        return mapToDto(ticket);
    }

    @Override
    public TicketDto updateTicket(TicketDto ticketDto, Long ticketId) {
        Ticket ticket =ticketRepository.findById(ticketId).orElseThrow(()-> new TicketNotFoundException("Ticket could not be updated"));
        ticket.setSeatNumber(ticketDto.getSeatNumber());
        ticket.setMaxKg(ticketDto.getMaxKg());
        ticket.setTypeOfTicket(ticketDto.getTypeOfTicket());
        ticket.setPrice(ticketDto.getPrice());

        Ticket updatedTicket = ticketRepository.save(ticket);
        return mapToDto(updatedTicket);

    }

    @Override
    public void deleteTicketId(Long ticketId) {
        Ticket ticket =ticketRepository.findById(ticketId).orElseThrow(()-> new TicketNotFoundException("Ticket could not be deleted"));
        ticketRepository.delete(ticket);
    }

    private TicketDto mapToDto(Ticket ticket){
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTickedId(ticket.getTickedId());
        ticketDto.setSeatNumber(ticket.getSeatNumber());
        ticketDto.setMaxKg(ticket.getMaxKg());
        ticketDto.setTypeOfTicket(ticket.getTypeOfTicket());
        ticketDto.setPrice(ticket.getPrice());
        return ticketDto ;
    }
    private Ticket mapToEntity (TicketDto ticketDto){
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(ticketDto.getSeatNumber());
        ticket.setMaxKg(ticketDto.getMaxKg());
        ticket.setTypeOfTicket(ticketDto.getTypeOfTicket());
        ticket.setPrice(ticketDto.getPrice());
        return ticket;
    }




}
