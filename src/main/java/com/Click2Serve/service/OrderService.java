package com.Click2Serve.service;

import com.Click2Serve.Dto.OrderCreateDTO;
import com.Click2Serve.Dto.OrderItemRequestDTO;
import com.Click2Serve.Dto.OrderItemResponseDTO;
import com.Click2Serve.Dto.OrderResponseDTO;
import com.Click2Serve.Entity.*;
import com.Click2Serve.Repository.*;
import com.Click2Serve.Status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menueItemRepository;


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

            MenuItem menuItem = menueItemRepository.findById(itemDTO.getItemId())
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


    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToResponse(order);
    }


    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByRoom(Long roomId) {

        return orderRepository.findByRoomId(roomId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private OrderResponseDTO mapToResponse(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setOrderTime(order.getOrderTime());

        dto.setRoomId(order.getRoom().getId());
        dto.setRoomNumber(order.getRoom().getRoomNumber());
        dto.setUserId(order.getUser().getId());


        List<OrderItemResponseDTO> items = order.getItems().stream().map(item -> {
            OrderItemResponseDTO i = new OrderItemResponseDTO();
            i.setItemId(item.getMenuItem().getId());
            i.setItemName(item.getMenuItem().getName());
            i.setQuantity(item.getQuantity());
            i.setPrice(item.getPrice());
            i.setTotalPrice(item.getTotalPrice());
            return i;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }
}
