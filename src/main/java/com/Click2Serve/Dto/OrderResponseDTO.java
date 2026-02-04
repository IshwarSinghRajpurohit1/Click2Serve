package com.Click2Serve.Dto;

import com.Click2Serve.Status.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderTime;

    private Long roomId;
    private String roomNumber;
    private Long userId;


    private List<OrderItemResponseDTO> items;
}

