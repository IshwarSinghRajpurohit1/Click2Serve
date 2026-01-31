package com.Click2Serve.service;

import com.Click2Serve.Dto.OrderDTO;
import com.Click2Serve.Entity.Order;
import com.Click2Serve.Status.OrderStatus;
import com.Click2Serve.Entity.Room;
import com.Click2Serve.Entity.User;
import com.Click2Serve.Repository.OrderRepository;
import com.Click2Serve.Repository.RoomRepository;
import com.Click2Serve.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public Order placeOrder(OrderDTO orderDTO) {

        Room room = roomRepository.findById(orderDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .orderDetails(orderDTO.getOrderDetails())
                .totalAmount(orderDTO.getTotalAmount())
                .room(room)
                .user(user)
                .status(OrderStatus.PENDING)
                .orderTime(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }

    public Order confirmOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only PENDING orders can be confirmed");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }
}
