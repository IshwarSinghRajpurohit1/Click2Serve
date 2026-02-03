package com.Click2Serve.Dto;

import lombok.Data;

@Data
public class OrderItemRequestDTO {
    private Long itemId;
    private Integer quantity;
}
