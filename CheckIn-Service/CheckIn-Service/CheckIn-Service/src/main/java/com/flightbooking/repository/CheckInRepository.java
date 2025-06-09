package com.flightbooking.repository;


import com.flightbooking.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    Optional<CheckIn> findBySeatNumber(int seatNumber);
    Optional<CheckIn> findByBookingId(String bookingId);
}

