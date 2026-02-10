package com.Click2Serve.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import com.Click2Serve.Entity.Category;
import com.Click2Serve.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController
{


        private final CategoryService categoryService;
        @PostMapping("/add/{hotelId}")
        public Category addCategory(@PathVariable Long hotelId, @RequestBody Category category) {

            return categoryService.createCategory(hotelId, category.getName());
        }




        @GetMapping("/get/{hotelId}")
        public List<Category> getCategoriesByHotel(@PathVariable Long hotelId) {
            return categoryService.getByHotel(hotelId);
        }

}