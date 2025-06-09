package com.flightbooking.service;

import com.flightbooking.feignClient.BookingService;
import com.flightbooking.model.BookingEntity;
import com.flightbooking.model.CheckIn;
import com.flightbooking.repository.CheckInRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CheckInService {

    @Autowired
   private BookingService bookingService;

    @Autowired
    private CheckInRepository checkInRepository;



    private static final int[] seats = new int[40];

    public ResponseEntity<String> processCheckIn(CheckIn request) {
        String bookingId = request.getBookingId();
        int seatNumber = request.getSeatNumber();

        List<String> bookings = bookingService.getAllBookingId(); // Moved here

        if (!bookings.contains(bookingId)) {
            return ResponseEntity.badRequest().body("Invalid Booking ID");
        }

        if (checkInRepository.findBySeatNumber(seatNumber).isPresent()) {
            return ResponseEntity.badRequest().body("Seat already booked");
        }

        if (checkInRepository.findByBookingId(bookingId).isPresent()) {
            return ResponseEntity.badRequest().body("This booking ID has already checked in");
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setBookingId(bookingId);
        checkIn.setSeatNumber(seatNumber);

        checkInRepository.save(checkIn);
        return ResponseEntity.ok("Checked in successfully. Seat: " + seatNumber);
    }


    public List<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }

    public Optional<CheckIn> findBySeatNumber(int seatNumber) {
        return checkInRepository.findBySeatNumber(seatNumber);
    }

    public Optional<CheckIn> findByBookingId(String bookingId) {
        return checkInRepository.findByBookingId(bookingId);
    }
}
