package com.Click2Serve.Repository;

import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.MenueItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenueItemRepository extends JpaRepository<MenueItem, Long> {

    // Admin: hotel + category ke sab items
    List<MenueItem> findByHotel_IdAndCategory(Long hotelId, Category category);

    // Customer: hotel + category ke sirf active items
    List<MenueItem> findByHotel_IdAndCategoryAndActiveTrue(Long hotelId, Category category);

    // Admin: all items of hotel, ignore category
    List<MenueItem> findByHotel_Id(Long hotelId);

    // Customer: all active items of hotel, ignore category
    List<MenueItem> findByHotel_IdAndActiveTrue(Long hotelId);
}
