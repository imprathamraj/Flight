package com.flightservice.controller;

import com.flightservice.model.FareRequest;
import com.flightservice.model.Flight;
import com.flightservice.service.impl.FlightServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
//TODO: admin Acess;

@RestController
@RequestMapping("/flight/admin")
public class AdminFlightController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FlightServiceImpl flightService;

    @PostMapping("/addNewFlight")
    public Flight addFlight(@RequestBody Flight flight) {

           Flight savedFlight= flightService.addFlight(flight);


           //extract fare date and add flightId to it
        FareRequest fareRequest= flight.getFareRequest();

        fareRequest.setFlightId(flight.getFlightId());

        Map<String, Object> fareData =new HashMap<>();
        fareData.put("flightId",fareRequest.getFlightId());
        fareData.put("economyFare",fareRequest.getEconomyFare());
        fareData.put("businessFare",fareRequest.getBusinessFare());


        //call fareService
       restTemplate.postForObject("http://FARE-SERVICE/fare/addNewFare", fareData ,String.class);

      return flight;


    }

    
    @PutMapping
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight) {


        Flight updatedFlight = flightService.updateFlight(flight);
        String flightId = updatedFlight.getFlightId();

        flight.setFlightId(flightId);

        // Prepare Fare object (not a Map)
       FareRequest fareData = new FareRequest();
        fareData.setFlightId(flightId);
        fareData.setEconomyFare(flight.getFareRequest().getEconomyFare());
        fareData.setBusinessFare(flight.getFareRequest().getBusinessFare());

        // Make PUT request using RestTemplate with correct path and body
        restTemplate.put(
                String.format("http://FARE-SERVICE/fare/updatefare/%s", flightId),
                fareData
        );

        return new ResponseEntity<>(flight, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
        restTemplate.getForObject("http://FARE-SERVICE/fare/"+id, FareRequest.class);
        return ResponseEntity.ok("Flight deleted successfully.");
    }
}
