package com.Click2Serve.service;

import com.Click2Serve.Dto.OrderCreateDTO;
import com.Click2Serve.Dto.OrderItemRequestDTO;
import com.Click2Serve.Entity.*;
import com.Click2Serve.Repository.*;
import com.Click2Serve.Status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MenueItemRepository menueItemRepository;



    @Transactional
    public Order placeOrder(OrderCreateDTO dto) {

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .room(room)
                .user(user)
                .status(OrderStatus.PENDING)
                .orderTime(LocalDateTime.now())
                .totalAmount(0.0)
                .build();

        order = orderRepository.save(order);

        double grandTotal = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {

            MenueItem menuItem = menueItemRepository.findById(itemDTO.getItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            double itemTotal = menuItem.getPrice() * itemDTO.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setTotalPrice(itemTotal);

            grandTotal += itemTotal;
            orderItems.add(orderItem);
        }

        order.setTotalAmount(grandTotal);
        order.setItems(orderItems);

        orderItemRepository.saveAll(orderItems);
        return orderRepository.save(order);
    }



    @Transactional
    public void confirmOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only PENDING orders can be confirmed");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
    }
}
