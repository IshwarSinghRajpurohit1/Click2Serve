package com.Click2Serve.Category.Controller;
import com.Click2Serve.Category.Entity.Category;
import com.Click2Serve.Category.Service.CategoryService;
import com.Click2Serve.Exception.ResponseClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController
{


        private final CategoryService categoryService;
        @PostMapping("/add/{hotelId}")
        public ResponseEntity<?> addCategory(@PathVariable Long hotelId, @RequestBody Category category)
        {
        
             return categoryService.createCategory(hotelId, category.getName());

          }

        @GetMapping("/get/{hotelId}")
        public List<Category> getCategoriesByHotel(@PathVariable Long hotelId)
        {
        return categoryService.getByHotel(hotelId);

        }

}