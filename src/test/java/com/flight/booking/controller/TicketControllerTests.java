package com.flight.booking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.booking.dto.TicketDto;
import com.flight.booking.dto.TicketResponse;
import com.flight.booking.model.Ticket;
import com.flight.booking.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers =TicketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TicketControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;


    private Ticket ticket;

    private TicketDto ticketDto;

    @BeforeEach
    public void init() {
        ticket = Ticket.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();
        ticketDto = TicketDto.builder()
                .typeOfTicket("economy")
                .price(200.00)
                .maxKg(20.00)
                .seatNumber("5AAAB").build();


    }

    @Test
    public void TicketController_CreateTicket_ReturnCreated() throws Exception {
        given(ticketService.createTicket(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));


        ResultActions response = mockMvc.perform(post("/api/auth/ticket/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeOfTicket", CoreMatchers.is(ticketDto.getTypeOfTicket())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(ticketDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxKg", CoreMatchers.is(ticketDto.getMaxKg())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatNumber", CoreMatchers.is(ticketDto.getSeatNumber())));
    }

    @Test
    public void TicketController_GetAllTicket_ReturnResponseDto() throws Exception {
        TicketResponse responseDto = TicketResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(ticketDto)).build();
        when(ticketService.getAllTickets(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/auth/ticket/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void TicketController_TicketDetail_ReturnTicketDto() throws Exception {
        Long ticketId = 1L;
        when(ticketService.getTicketById(ticketId)).thenReturn(ticketDto);

        ResultActions response = mockMvc.perform(get("/api/auth/ticket/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeOfTicket", CoreMatchers.is(ticketDto.getTypeOfTicket())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(ticketDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxKg", CoreMatchers.is(ticketDto.getMaxKg())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatNumber", CoreMatchers.is(ticketDto.getSeatNumber())));
    }

    @Test
    public void TicketController_UpdateTicket_ReturnTicketDto() throws Exception {
        Long ticketId = 1L;
        when(ticketService.updateTicket(ticketDto, ticketId)).thenReturn(ticketDto);

        ResultActions response = mockMvc.perform(put("/api/auth/ticket/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeOfTicket", CoreMatchers.is(ticketDto.getTypeOfTicket())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(ticketDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxKg", CoreMatchers.is(ticketDto.getMaxKg())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatNumber", CoreMatchers.is(ticketDto.getSeatNumber())));
    }

    @Test
    public void TicketController_DeleteTicket_ReturnVoid() throws Exception {
        Long ticketId =1L;
        doNothing().when(ticketService).deleteTicketId(ticketId);

        ResultActions response = mockMvc.perform(delete("/api/auth/ticket/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}