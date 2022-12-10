package com.flight.booking.controller;



import com.flight.booking.dto.AircraftResponse;
import com.flight.booking.dto.AirportDto;
import com.flight.booking.dto.AirportResponse;
import com.flight.booking.dto.TicketDto;
import com.flight.booking.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AirportController {

    private AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("airport/all")
    public ResponseEntity<AirportResponse> getAirport(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity(airportService.getAllAirports(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("airport/{airportId}")
    public ResponseEntity<AirportDto> airportDetail(@PathVariable Long airportId) {
        return ResponseEntity.ok(airportService.getAirportById(airportId));

    }

    @PostMapping("airport/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AirportDto> createAirport(@RequestBody AirportDto airportDto) {
        return new ResponseEntity<>(airportService.createAirport(airportDto), HttpStatus.CREATED);
    }

    @PutMapping("airport/{airportId}/update")

    public ResponseEntity<AirportDto> updateAirport(@RequestBody AirportDto airportDto, @PathVariable("airportId") Long airportId) {
        AirportDto response = airportService.updateAirport(airportDto, airportId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("airport/{airportId}/delete")
    public ResponseEntity<String> deleteAirport(@PathVariable("airportId") Long airportId) {
        airportService.deleteAirportId(airportId);
        return new ResponseEntity<>("Airport deleted successfully !", HttpStatus.OK);

    }
}