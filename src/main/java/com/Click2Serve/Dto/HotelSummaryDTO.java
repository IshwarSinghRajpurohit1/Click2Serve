package com.Click2Serve.Dto;

import com.Click2Serve.Status.HotelStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelSummaryDTO
{
    private Long id;
    private String hotelName;
    private String address;
    private HotelStatus status;
}
