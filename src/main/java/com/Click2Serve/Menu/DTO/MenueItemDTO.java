package com.Click2Serve.Menu.DTO;

import lombok.Data;

@Data
public class MenueItemDTO
  {
        private Long id;
        private String name;
        private Double price;
        private Boolean active;
        private Long hotelId;
  }


