package com.Click2Serve.Room.Entity;

import com.Click2Serve.Hotel.Entity.Hotel;
import com.Click2Serve.Order.Entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private String roomType;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Order> orders;
}

