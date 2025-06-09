package com.fareservice.repository;

import com.fareservice.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepo extends JpaRepository<Fare,String> {
}
