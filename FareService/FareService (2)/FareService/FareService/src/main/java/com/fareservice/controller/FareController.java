package com.fareservice.controller;

import com.fareservice.model.Fare;
import com.fareservice.service.impl.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fare")
public class FareController {

    @Autowired
    private FareService fareService;

    @PostMapping("/addNewFare")
    public ResponseEntity<Fare> addFare(@RequestBody Fare fare) {
        Fare newFare = fareService.addFare(fare);
        return new ResponseEntity<>(newFare, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Fare>> getAllFares() {
        List<Fare> fares = fareService.getAllFares();
        return new ResponseEntity<>(fares, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fare> getFareById(@PathVariable String id) {
           Fare fare = fareService.getFareById(id);
        return  new ResponseEntity<>(fare, HttpStatus.OK);

    }

    @PutMapping("/updatefare/{id}")
    public ResponseEntity<Fare> updateFare(@PathVariable String id, @RequestBody Fare fare) {
        Fare updatedFare = fareService.updateFare(id,fare);
        return updatedFare != null ? new ResponseEntity<>(updatedFare, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFare(@PathVariable String id) {
        fareService.deleteFare(id);
        return new ResponseEntity<>("Fare deleted successfully.", HttpStatus.OK);
    }
}
