package com.Click2Serve.Dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long roomId;
    private Long userId;
    private String orderDetails;
    private double totalAmount;
}