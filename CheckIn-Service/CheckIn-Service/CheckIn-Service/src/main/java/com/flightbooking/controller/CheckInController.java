package com.flightbooking.controller;


import com.flightbooking.feignClient.BookingService;
import com.flightbooking.feignClient.NotificationService;
import com.flightbooking.model.BookingEntity;
import com.flightbooking.model.CheckIn;
import com.flightbooking.model.Email;
import com.flightbooking.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/checkin/user")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BookingService bookingService;


    @PostMapping
    public ResponseEntity<String> checkIn(@RequestBody CheckIn request) {

        // Step 1: Perform check-in
        ResponseEntity<String> response = checkInService.processCheckIn(request);

        // Step 2: Send message only if check-in succeeded
        if (response.getStatusCode() == HttpStatus.OK) {

            // Fetch booking details to get user info
            BookingEntity booking = bookingService.getBookingById(request.getBookingId());

            if (booking != null && booking.getEmail() != null && !booking.getEmail().isEmpty()) {
                Email email = new Email();
                email.setTo(booking.getEmail());
                email.setSubject("Flight Check-In Confirmation");

                email.setBody(
                        "Dear " + booking.getName() + ",\n\n" +
                                "Your check-in has been successfully completed.\n\n" +
                                "Booking ID   : " + booking.getBookingId() + "\n" +
                                "Flight ID    : " + booking.getFlightId() + "\n" +
                                "Passenger    : " + booking.getName() + "\n\n" +
                                "Sear number  : " + request.getSeatNumber() + "\n\n" +
                                "Please arrive at the airport on time with valid ID proof.\n\n" +
                                "Thank you,\n" +
                                "Flight Reservation Team"
                );

                notificationService.message(email);
            }
        }

        // Step 3: Return original response
        return response;
    }

    @GetMapping("/seat/{seatNumber}")
    public  ResponseEntity<?>  getBySeatNumber(@PathVariable int seatNumber) {

        if (seatNumber < 1 || seatNumber > 40) {
            return ResponseEntity.badRequest().body("Seat number should be between 1 and 40");
        }


        Optional<CheckIn> checkIn = checkInService.findBySeatNumber(seatNumber);
        if (checkIn.isPresent()) {
          return new ResponseEntity<>(checkIn, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Seat not booked yet", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getByBookingId(@PathVariable String bookingId) {
        Optional<CheckIn> checkIn = checkInService.findByBookingId(bookingId);

        if (checkIn.isPresent()) {
            return new ResponseEntity<>(checkIn, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No-check Is found for this Booking Id", HttpStatus.OK);
        }
    }
}