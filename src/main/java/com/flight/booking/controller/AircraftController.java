package com.flight.booking.controller;


import com.flight.booking.dto.AircraftDto;
import com.flight.booking.dto.AircraftResponse;
import com.flight.booking.dto.AirportDto;
import com.flight.booking.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AircraftController {
    private AircraftService aircraftService;

    @Autowired

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }


    @GetMapping("aircraft/all")
    public ResponseEntity<AircraftResponse> getAircraft(
            @RequestParam(value="pageNo",defaultValue="0" ,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize){
        return  new ResponseEntity(aircraftService.getAllAircraft(pageNo, pageSize),HttpStatus.OK);
    }
    @GetMapping("aircraft/{aircraftId}")
    public  ResponseEntity <AircraftDto>  aircraftDetail(@PathVariable Long aircraftId) {
        return ResponseEntity.ok(aircraftService.getAircraftById(aircraftId));

    }
    @PostMapping("aircraft/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AircraftDto> createAircraft(@RequestBody AircraftDto aircraftDto){
        return new ResponseEntity<>(aircraftService.createAircraft(aircraftDto), HttpStatus.CREATED);
    }

    @PutMapping("aircraft/{aircraftId}/update")

    public ResponseEntity<AircraftDto> updateAircraft(@RequestBody AircraftDto aircraftDto, @PathVariable("aircraftId") Long aircraftId) {
        AircraftDto response = aircraftService.updateAircraft(aircraftDto, aircraftId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("aircraft/{aircraftId}/delete")
    public ResponseEntity<String> deleteAircraft(@PathVariable("aircraftId") Long aircraftId) {
        aircraftService.deleteAircraftId(aircraftId);
        return new ResponseEntity<>("Aircraft deleted successfully !", HttpStatus.OK);

    }
}
