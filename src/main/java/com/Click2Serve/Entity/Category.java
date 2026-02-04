package com.Click2Serve.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private Boolean Active ;
        private boolean enabled = true;


        @ManyToOne
        @JoinColumn(name = "hotel_id")
        @JsonIgnore
        private Hotel hotel;
}
