package com.Click2Serve.Controller;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RestaurantOrder {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hotelId;
    private String tableNumber;
    private Double totalAmount;
    private String status; // PENDING, SERVED, CANCELLED
}