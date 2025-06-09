package com.flightbooking.feignClient;

import com.flightbooking.model.BookingEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "BOOKING-SERVICE")
public interface BookingService {

    @GetMapping("/bookings/admin/id")
    List<String> getAllBookingId();

    @GetMapping("/bookings/user/{id}")
    public BookingEntity getBookingById(@PathVariable String id);
}
