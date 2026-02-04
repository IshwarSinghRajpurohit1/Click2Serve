package com.Click2Serve.Entity;

import jakarta.persistence.*;
import lombok.Data;
import com.Click2Serve.Entity.Hotel;



@Entity
@Table(name = "qr_master")
@Data
public class QrMaster {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String qrCode;

        private String status;

        private String qrImagePath;

        @ManyToOne
        @JoinColumn(name = "hotel_id")
        private Hotel hotel;
}
