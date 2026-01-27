package com.Click2Serve.Entity;

import jakarta.persistence.*;

@Entity
public class MenueItem
{


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private Double price;
        private String status;
        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;
    }



