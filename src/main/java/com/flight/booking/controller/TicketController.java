package com.flight.booking.controller;


import com.flight.booking.dto.TicketDto;
import com.flight.booking.dto.TicketResponse;
import com.flight.booking.model.Ticket;
import com.flight.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("ticket/all")
    public ResponseEntity<TicketResponse> getTickets(
            @RequestParam(value="pageNo",defaultValue="0" ,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize
    ){

    return new ResponseEntity(ticketService.getAllTickets(pageNo,pageSize),HttpStatus.OK)    ;

    }
    @GetMapping("ticket/{ticketId}")
    public  ResponseEntity <TicketDto>  ticketDetail(@PathVariable Long ticketId) {
       return ResponseEntity.ok(ticketService.getTicketById(ticketId));

    }



    @PostMapping("ticket/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto){
        return new ResponseEntity<>(ticketService.createTicket(ticketDto), HttpStatus.CREATED);
    }

    @PutMapping("ticket/{ticketId}/update")

    public ResponseEntity<TicketDto> updateTicket(@RequestBody TicketDto ticketDto ,@PathVariable("ticketId") Long ticketId){
   TicketDto response = ticketService.updateTicket(ticketDto,ticketId);
        return new ResponseEntity(response,HttpStatus.OK);
    }
   @DeleteMapping("ticket/{ticketId}/delete")
    public  ResponseEntity<String> deleteTicket(@PathVariable ("ticketId") Long ticketId){
        ticketService.deleteTicketId(ticketId);
        return new ResponseEntity<>("Ticket deleted successfully !",HttpStatus.OK);
   }

}
