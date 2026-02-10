package com.Click2Serve.Menu.DTO;

import lombok.Data;

@Data
public class MenuCreateDTO
{
    private String name;
    private Double price;
    private Long hotelId;
    private Long categoryId;
}
