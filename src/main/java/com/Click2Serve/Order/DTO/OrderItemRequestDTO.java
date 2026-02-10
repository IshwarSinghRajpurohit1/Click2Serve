package com.Click2Serve.Order.DTO;

import lombok.Data;

@Data
public class OrderItemRequestDTO {
    private Long itemId;
    private Integer quantity;
}
