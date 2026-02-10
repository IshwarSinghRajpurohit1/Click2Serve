package com.Click2Serve.Order.DTO;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long itemId;
    private String itemName;
    private int quantity;
    private double price;
    private double totalPrice;
}
