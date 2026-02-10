package com.Click2Serve.Order.DTO;

import lombok.Data;
import java.util.List;

@Data
public class OrderCreateDTO {
    private Long roomId;
    private Long userId;
    private List<OrderItemRequestDTO> items;
}
