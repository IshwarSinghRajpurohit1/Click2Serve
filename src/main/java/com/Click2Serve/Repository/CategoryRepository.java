package com.Click2Serve.Repository;

import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.Hotel;
import com.Click2Serve.Entity.QrMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 👤 Customer//
    List<Category> findByHotelAndEnabledTrue(Hotel hotel);

    // 🔐 Admin//
    List<Category> findByHotel(Hotel hotel);

    List<Category> findByHotel_Id(Long hotelId);
}
