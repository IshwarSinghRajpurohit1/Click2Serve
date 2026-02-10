package com.Click2Serve.Category.Repository;

import com.Click2Serve.Category.Entity.Category;
import com.Click2Serve.Hotel.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {


    List<Category> findByHotelAndEnabledTrue(Hotel hotel);

    List<Category> findByHotel(Hotel hotel);

    List<Category> findByHotel_Id(Long hotelId);
}
