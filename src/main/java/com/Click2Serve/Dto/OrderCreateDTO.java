package com.Click2Serve.Dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderCreateDTO {
    private Long roomId;
    private Long userId;
    private List<OrderItemRequestDTO> items;
}
