package com.Click2Serve.Dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    private Long hotelId;
    private String tableNumber;
    private List<CartItemDTO> items;
}
