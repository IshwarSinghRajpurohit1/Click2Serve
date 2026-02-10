package com.Click2Serve.Order.Entity;

import com.Click2Serve.Room.Entity.Room;
import com.Click2Serve.Users.Entity.User;
import com.Click2Serve.Status.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderDetails;
    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderTime;

    private boolean confirmed;
}

