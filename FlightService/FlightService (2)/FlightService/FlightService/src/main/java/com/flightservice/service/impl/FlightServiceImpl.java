package com.flightservice.service.impl;

import com.flightservice.model.Flight;
import com.flightservice.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlightServiceImpl{

    @Autowired
    private FlightRepo flightRepository;

    public Flight addFlight(Flight flight) {

//        UUID id= UUID.randomUUID();
//        flight.setFlightId(id.toString());
        return flightRepository.save(flight);
    }


    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }


    public Flight getFlightById(String id) {
        return flightRepository.findById(id).orElseThrow(()->new RuntimeException("UserId not found"));
    }


    public Flight updateFlight(Flight updatedFlight) {
            return flightRepository.save(updatedFlight);
    }


    public void deleteFlight(String id) {
        flightRepository.deleteById(id);
    }
}
