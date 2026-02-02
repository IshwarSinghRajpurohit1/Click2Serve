package com.Click2Serve.service;

import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.Hotel;
import com.Click2Serve.Repository.CategoryRepository;
import com.Click2Serve.Repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final HotelRepository hotelRepository;

    // 1. Category Create Karne ka Logic
    public Category createCategory(Long hotelId, String name) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        Category category = new Category();
        category.setName(name);
        category.setHotel(hotel);
        category.setEnabled(true);
        category.setActive(true); // Aapki entity mein dono fields hain toh dono set kar dete hain

        return categoryRepository.save(category);
    }

    // 2. Hotel ke hisaab se categories fetch karne ka logic (Jo error de raha tha)
    public List<Category> getByHotel(Long hotelId) {
        return categoryRepository.findByHotel_Id(hotelId);
    }
}
