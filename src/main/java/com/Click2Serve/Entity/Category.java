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
        @JsonIgnore

      @ManyToOne
        @JoinColumn(name = "hotel_id")
        private Hotel hotel;


}
