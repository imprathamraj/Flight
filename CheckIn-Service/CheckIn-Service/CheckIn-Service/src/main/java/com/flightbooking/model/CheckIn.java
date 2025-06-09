package com.flightbooking.model;


  import jakarta.persistence.*;
  import lombok.AllArgsConstructor;
  import lombok.Data;
  import lombok.NoArgsConstructor;

  import java.util.UUID;

@Entity
    @Table(name = "checkins")
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class CheckIn {

        @Id
        private String id;

        @Column(nullable = false, unique = true)
        private String bookingId;

        @Column(nullable = false, unique = true)
        private int seatNumber;

        @PrePersist
        public void generateId() {
            if (this.id == null) {
                this.id = UUID.randomUUID().toString()
                        .replace("-", "")
                        .substring(0, 8);  // 8-char ID
            }
}}