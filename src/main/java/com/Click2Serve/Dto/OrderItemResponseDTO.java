package com.Click2Serve.Dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long itemId;
    private String itemName;
    private int quantity;
    private double price;
    private double totalPrice;
}
