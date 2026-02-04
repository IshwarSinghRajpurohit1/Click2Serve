package com.Click2Serve.Entity;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Table(name = "menu_items")
@Entity
public class MenuItem
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private Double price;
        private String status;

        private Boolean active;
        @ManyToOne
        @JoinColumn(name = "category_id",nullable = true)
        private Category category;

     @ManyToOne
      @JoinColumn(name = "hotel_id",nullable = false)
      private Hotel hotel;


}
