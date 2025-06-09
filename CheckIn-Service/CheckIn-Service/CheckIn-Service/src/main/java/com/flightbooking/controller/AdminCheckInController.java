package com.flightbooking.controller;

import com.flightbooking.model.CheckIn;
import com.flightbooking.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkin/admin")
public class AdminCheckInController {

    @Autowired
    private CheckInService checkInService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCheckIns() {
        List<CheckIn> checkIns = checkInService.getAllCheckIns();

        if (checkIns.isEmpty()) {
            return new ResponseEntity<>("No check-in records found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(checkIns, HttpStatus.OK);
        }
    }

}
