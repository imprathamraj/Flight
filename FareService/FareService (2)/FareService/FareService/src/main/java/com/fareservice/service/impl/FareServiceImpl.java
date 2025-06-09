package com.fareservice.service.impl;

import com.fareservice.model.Fare;
import com.fareservice.repository.FareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FareServiceImpl implements FareService {

    @Autowired
    private FareRepo fareRepository;

    @Override
    public Fare addFare(Fare fare) {
        return fareRepository.save(fare);
    }

    @Override
    public List<Fare> getAllFares() {
        return fareRepository.findAll();
    }

    @Override
    public Fare getFareById(String id) {
        return fareRepository.findById(id).orElseThrow(()->new RuntimeException("UserId not found"));
    }

    @Override
    public Fare updateFare(String id, Fare updatedFare) {
        Fare fare = fareRepository.findById(id).orElse(null);
        if (fare != null) {
            fare.setFlightId(updatedFare.getFlightId());
            fare.setEconomyFare(updatedFare.getEconomyFare());
             fare.setBusinessFare(updatedFare.getBusinessFare());
            return fareRepository.save(fare);
        }
        return null;
    }

    @Override
    public void deleteFare(String id) {
        fareRepository.deleteById(id);
    }
}
