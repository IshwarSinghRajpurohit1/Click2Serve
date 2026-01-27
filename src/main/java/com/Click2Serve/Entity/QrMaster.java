package com.Click2Serve.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "qr_master")
public class QrMaster {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(unique = true)
        private String qrCode;
        private String status; // ACTIVE/INACTIVE
        @ManyToOne
        @JoinColumn(name = "hotel_id")
        private Hotel hotel;
        // getters & setters
    }

}
