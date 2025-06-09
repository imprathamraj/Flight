package com.flightbooking.BookingService.controller;

import com.flightbooking.BookingService.dto.BookingRequest;
import com.flightbooking.BookingService.dto.Email;
import com.flightbooking.BookingService.dto.StripeResponse;
import com.flightbooking.BookingService.feignclient.NotificationService;
import com.flightbooking.BookingService.feignclient.PaymentGateway;
import com.flightbooking.BookingService.model.BookingEntity;
import com.flightbooking.BookingService.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings/user")
public class BookingController {

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private IBookingService bookingService; // Use Interface for Loose Coupling


    @PostMapping
    public StripeResponse createBooking(@RequestBody BookingEntity booking) {

        BookingRequest request= new BookingRequest();

        request.setName("Flight-Ticket");
        request.setAmount(booking.getPaymentAmount());
        request.setQuantity(1L);
        request.setCurrency("INR");

        StripeResponse stripe = paymentGateway.checkoutProduct(request);

        if (stripe == null || !"success".equalsIgnoreCase(stripe.getStatus())) {
            throw new RuntimeException("Payment unsuccessful, booking not created.");
        }
        booking.setPaymentId(stripe.getSessionId());

        bookingService.createBooking(booking);

        Email email=new Email();
        email.setTo(booking.getEmail());
        email.setSubject("Flight Booking Confirmation");

        email.setBody(
                "Dear " + booking.getName() + ",\n\n" +
                        "Thank you for booking with us! Your flight has been successfully booked.\n\n" +
                        "Booking Details:\n" +
                        "Booking ID   : " + booking.getBookingId() + "\n" +
                        "Flight ID    : " + booking.getFlightId() + "\n" +
                        "Amount Paid  : " + booking.getPaymentAmount() + " INR\n\n" +
                        "We wish you a pleasant journey!\n\n" +
                        "Best regards,\n" +
                        "Flight Reservation Team"
        );
        notificationService.message(email);

        return stripe;
    }



    @GetMapping("/{id}")
    public BookingEntity getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    public BookingEntity updateBooking(@PathVariable String id, @RequestBody BookingEntity updatedBooking) {

        // Update the booking
        BookingEntity booking = bookingService.updateBooking(id, updatedBooking);

        // Prepare and send email notification
        Email email = new Email();
        email.setTo(booking.getEmail());
        email.setSubject("Flight Booking Update Confirmation");

        email.setBody(
                "Dear " + booking.getName() + ",\n\n" +
                        "Your flight booking has been successfully updated.\n\n" +
                        "Updated Booking Details:\n" +
                        "Booking ID   : " + booking.getBookingId() + "\n" +
                        "Flight ID    : " + booking.getFlightId() + "\n" +
                        "Amount Paid  : " + booking.getPaymentAmount() + " INR\n\n" +
                        "If you did not request this update or need assistance, please contact our support team.\n\n" +
                        "Thank you,\n" +
                        "Flight Reservation Team"
        );

        notificationService.message(email);

        return booking;
    }



    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable String id) {

        // Fetch the booking from database/service before deleting
        BookingEntity entity = bookingService.getBookingById(id);
        if (entity == null) {
            return "Booking with ID " + id + " not found.";
        }

        // Prepare and send email
        Email email = new Email();
        email.setTo(entity.getEmail());
        email.setSubject("Flight Booking Cancellation Notice");

        email.setBody(
                "Dear " + entity.getName() + ",\n\n" +
                        "We would like to inform you that your flight booking has been successfully cancelled.\n\n" +
                        "Booking Details:\n" +
                        "Booking ID: " + entity.getBookingId() + "\n" +
                        "Flight ID: " + entity.getFlightId() + "\n" +
                        "Amount Paid: " + entity.getPaymentAmount()/100 + "\n\n" +
                        "If you did not request this cancellation or have any concerns, please contact our support team immediately.\n\n" +
                        "Thank you for using our service,\n" +
                        "Flight Reservation Team"
        );


        notificationService.message(email);

        // Delete the booking
        bookingService.deleteBooking(id);
        return "Booking with ID " + id + " deleted successfully.";
    }



}
