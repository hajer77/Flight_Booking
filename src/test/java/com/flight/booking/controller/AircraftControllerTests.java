package com.flight.booking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.booking.dto.AircraftDto;
import com.flight.booking.dto.AircraftResponse;
import com.flight.booking.model.Aircraft;
import com.flight.booking.service.AircraftService;
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

@WebMvcTest(controllers =AircraftController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AircraftControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AircraftService aircraftService;

    @Autowired
    private ObjectMapper objectMapper;

    private Aircraft aircraft;

    private AircraftDto aircraftDto;

    @BeforeEach
    public void init() {
        aircraft = Aircraft.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();
        aircraftDto  = AircraftDto.builder()
                .model("model1")
                .manufacturer("manufacturer1")
                .passengerCapacity(300).build();


    }

    @Test
    public void AircraftController_CreateAircraft_ReturnCreated() throws Exception {
        given(aircraftService.createAircraft(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));


        ResultActions response = mockMvc.perform(post("/api/auth/aircraft/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aircraftDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(aircraftDto.getModel())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer", CoreMatchers.is(aircraftDto.getManufacturer())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passengerCapacity", CoreMatchers.is(aircraftDto.getPassengerCapacity())));
    }

    @Test
    public void AircraftController_GetAllAircraft_ReturnResponseDto() throws Exception {
        AircraftResponse responseDto = AircraftResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(aircraftDto)).build();
        when(aircraftService.getAllAircraft(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/auth/aircraft/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }
    @Test
    public void AircraftController_AircraftDetail_ReturnAircraftDto() throws Exception {
        Long aircraftId = 1L;
        when(aircraftService.getAircraftById(aircraftId)).thenReturn(aircraftDto);

        ResultActions response = mockMvc.perform(get("/api/auth/aircraft/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aircraftDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(aircraftDto.getModel())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer", CoreMatchers.is(aircraftDto.getManufacturer())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passengerCapacity", CoreMatchers.is(aircraftDto.getPassengerCapacity())));
    }

    @Test
    public void AircraftController_UpdateAircraft_ReturnAircraftDto() throws Exception {
        Long aircraftId = 1L;
        when(aircraftService.updateAircraft(aircraftDto, aircraftId)).thenReturn(aircraftDto);

        ResultActions response = mockMvc.perform(put("/api/auth/aircraft/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aircraftDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(aircraftDto.getModel())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer", CoreMatchers.is(aircraftDto.getManufacturer())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passengerCapacity", CoreMatchers.is(aircraftDto.getPassengerCapacity())));
    }
    @Test
    public void AircraftController_DeleteAircraft_ReturnString() throws Exception {
        Long aircraftId =1L;
        doNothing().when(aircraftService).deleteAircraftId(1L);

        ResultActions response = mockMvc.perform(delete("/api/auth/aircraft/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}
