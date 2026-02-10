package com.Click2Serve.Category.DTO;

import com.Click2Serve.Menu.DTO.MenueItemDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryMenuDTO {

    private String categoryName;
    private Long categoryId;
    private List<MenueItemDTO> items;

}
