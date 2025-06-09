package com.flightbooking.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    private String bookingId;
    private String userId;
    private String flightId;
    private String name;
    private int age;
    private String email;
    private String address;
    private String fromCity;
    private String toCity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date departure;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date arrival;

    private String paymentId;
    private Long paymentAmount;


}
