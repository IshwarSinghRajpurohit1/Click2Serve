package com.Click2Serve.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryMenuDTO {

    private String categoryName;
    private Long categoryId;
    private List<MenueItemDTO> items;

}
