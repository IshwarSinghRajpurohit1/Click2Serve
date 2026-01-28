package com.Click2Serve.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String status;
        @ManyToOne
        @JoinColumn(name = "hotel_id")
        private Hotel hotel;


}
