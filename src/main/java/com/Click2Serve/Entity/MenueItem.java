package com.Click2Serve.Entity;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class MenueItem
{


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private Double price;
        private String status;

        private Boolean active;
        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;

}



