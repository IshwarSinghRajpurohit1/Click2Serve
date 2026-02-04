package com.Click2Serve.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hotelId;
    private String tableNumber;
    private Double totalAmount;
    private String status; // PENDING, SERVED, COMPLETED

    // Ye mapping jaroori hai
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "room_id") // Ye Database mein column banayega
    private Room room;

    // Helper method (Zaroori hai)
    public void addOrderItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}