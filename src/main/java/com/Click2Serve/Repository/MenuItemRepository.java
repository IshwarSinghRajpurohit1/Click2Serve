package com.Click2Serve.Repository;

import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {


    List<MenuItem> findByHotel_IdAndCategory(Long hotelId, Category category);


    List<MenuItem> findByHotel_IdAndCategoryAndActiveTrue(Long hotelId, Category category);

    List<MenuItem> findByHotel_Id(Long hotelId);

    List<MenuItem> findByHotel_IdAndActiveTrue(Long hotelId);
}
