package com.flight.booking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.booking.dto.AirportDto;
import com.flight.booking.dto.AirportResponse;
import com.flight.booking.model.Airport;
import com.flight.booking.service.AirportService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers =AirportController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AirportControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;


    @Autowired
    private ObjectMapper objectMapper;

    private Airport airport;

    private AirportDto airportDto;

    @BeforeEach
    public void init() {
        airport = Airport.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();
        airportDto = AirportDto.builder()
                .airportCode("Code")
                .airportName("Name")
                .city("city")
                .state("state")
                .country("country").build();

    }
        @Test
        public void AirportController_CreateAirport_ReturnCreated() throws Exception {
            given(airportService.createAirport(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));


            ResultActions response = mockMvc.perform(post("/api/auth/airport/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(airportDto)));

            response.andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.airportCode", CoreMatchers.is(airportDto.getAirportCode())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.airportName", CoreMatchers.is(airportDto.getAirportName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(airportDto.getCity())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.state", CoreMatchers.is(airportDto.getState())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(airportDto.getCountry())));

        }

    @Test
    public void AirportController_GetAllAirport_ReturnResponseDto() throws Exception {
        AirportResponse responseDto = AirportResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(airportDto)).build();
        when(airportService.getAllAirports(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/auth/airport/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }
    @Test
    public void AirportController_AirportDetail_ReturnAirportDto() throws Exception {
        Long airportId = 1L;
        when(airportService.getAirportById(airportId)).thenReturn(airportDto);

        ResultActions response = mockMvc.perform(get("/api/auth/airport/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(airportDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.airportCode", CoreMatchers.is(airportDto.getAirportCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.airportName", CoreMatchers.is(airportDto.getAirportName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(airportDto.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state", CoreMatchers.is(airportDto.getState())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(airportDto.getCountry())));
    }
    @Test
    public void AirportController_UpdateAirport_ReturnAirportDto() throws Exception {
        Long airportId = 1L;
        when(airportService.updateAirport(airportDto, airportId)).thenReturn(airportDto);

        ResultActions response = mockMvc.perform(put("/api/auth/airport/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(airportDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.airportCode", CoreMatchers.is(airportDto.getAirportCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.airportName", CoreMatchers.is(airportDto.getAirportName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(airportDto.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state", CoreMatchers.is(airportDto.getState())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(airportDto.getCountry())));
    }
    @Test
    public void AirportController_DeleteAirport_ReturnString() throws Exception {
        Long airportId =1L;
        doNothing().when(airportService).deleteAirportId(1L);

        ResultActions response = mockMvc.perform(delete("/api/auth/airport/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }




}
