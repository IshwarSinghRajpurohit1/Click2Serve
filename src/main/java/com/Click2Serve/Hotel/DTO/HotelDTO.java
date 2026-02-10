package com.Click2Serve.Hotel.DTO;

import lombok.Data;

@Data
public class HotelDTO {
    private Long id;
    private String hotelName;
    private String ownerName;
    private String address;
    private String phone;
    private String email;
    private String status;
}
