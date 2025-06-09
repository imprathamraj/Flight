package com.flightservice.controller;

import com.flightservice.model.FareRequest;
import com.flightservice.model.Flight;
import com.flightservice.service.impl.FlightServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

//TODO: User access

@RestController
@RequestMapping("/flight/user")
public class FlightUserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FlightServiceImpl flightService;

    @GetMapping("/all")
    public ResponseEntity<List<Flight>> getAllFlights() {

        List<Flight> list= flightService.getAllFlights();

        List<Flight> flights= list.stream().map(flight -> {

            FareRequest fare= restTemplate.getForObject("http://FARE-SERVICE/fare/"+ flight.getFlightId(), FareRequest.class);

            flight.setFareRequest(fare);
            return flight;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable String id) {
        Flight flight= flightService.getFlightById(id);
        FareRequest fare= restTemplate.getForObject("http://FARE-SERVICE/fare/"+id, FareRequest.class);
        flight.setFareRequest(fare);
        return ResponseEntity.ok(flight);
    }
}
