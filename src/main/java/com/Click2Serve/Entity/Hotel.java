package com.Click2Serve.Entity;

import com.Click2Serve.Status.HotelStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String hotelName;
        private String ownerName;
        private String address;
        private String phone;
        private String email;

        @Enumerated(EnumType.STRING)
        private HotelStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MenuItem> menuItems;

}


