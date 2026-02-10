package com.Click2Serve.Hotel.Repository;

import com.Click2Serve.Hotel.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel ,Long>
{

}
