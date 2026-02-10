package com.Click2Serve.Menu.DTO;

import com.Click2Serve.Hotel.DTO.HotelSummaryDTO;
import lombok.Data;

@Data
public class MenuResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private Boolean active;
    private HotelSummaryDTO hotel;
}
