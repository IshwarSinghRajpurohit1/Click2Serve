package com.Click2Serve.Category.Service;

import com.Click2Serve.Category.Entity.Category;
import com.Click2Serve.Exception.ResponseClass;
import com.Click2Serve.Hotel.Entity.Hotel;
import com.Click2Serve.Category.Repository.CategoryRepository;
import com.Click2Serve.Hotel.Repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final HotelRepository hotelRepository;


    public ResponseEntity<?> createCategory(Long hotelId, String name) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        Category category = new Category();
        category.setName(name);
        category.setHotel(hotel);
        category.setEnabled(true);
        category.setActive(true);

       Category saved =        categoryRepository.save(category);
         return ResponseClass.responseSuccess("Category created","Category",saved);
    }


    public List<Category> getByHotel(Long hotelId)
    {

                List <Category>  categories   =categoryRepository.findByHotel_Id(hotelId);
                 return (List<Category>) ResponseClass.responseSuccess("Category get success","Categories" , categories);
    }
}
