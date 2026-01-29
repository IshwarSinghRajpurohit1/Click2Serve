package com.Click2Serve.Entity;

import com.Click2Serve.Status.HotelStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
}
