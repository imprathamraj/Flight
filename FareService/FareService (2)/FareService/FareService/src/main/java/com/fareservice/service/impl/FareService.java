package com.fareservice.service.impl;

import com.fareservice.model.Fare;

import java.util.List;
import java.util.Optional;

public interface FareService {
    Fare addFare(Fare fare);
    List<Fare> getAllFares();
    Fare getFareById(String id);
    Fare updateFare(String id, Fare updatedFare);
    void deleteFare(String id);
}
