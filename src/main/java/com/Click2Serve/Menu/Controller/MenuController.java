package com.Click2Serve.Menu.Controller;
import com.Click2Serve.Category.DTO.CategoryMenuDTO;
import com.Click2Serve.Menu.DTO.MenuResponseDTO;
import com.Click2Serve.Category.Entity.Category;
import com.Click2Serve.Menu.Entity.MenuItem;
import com.Click2Serve.Menu.Service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @GetMapping("/customer/nested/{hotelId}")
    public List<CategoryMenuDTO> getNestedMenu(@PathVariable Long hotelId) {

        return menuService.getHotelMenuNested(hotelId);
    }


    @PostMapping("/add")
    public MenuResponseDTO addMenu(@RequestBody MenuItem item) {


        if (item.getHotel() == null || item.getHotel().getId() == null) {
            throw new RuntimeException("Hotel ID is required");
        }


        if (item.getCategory() != null && item.getCategory().getId() != null) {
            Category category = menuService.getCategoryById(item.getCategory().getId());
            item.setCategory(category);
        } else {
            item.setCategory(null);
        }


        if (item.getActive() == null) {
            item.setActive(true);
        }

        return menuService.addMenuItem(item); // ✅ DTO return ho raha hai
    }




    @GetMapping("/customer/{hotelId}")
    public List<MenuItem> getMenuForCustomer(
            @PathVariable Long hotelId,
            @RequestParam(required = false) Long categoryId) {

        Category category = null;
        if (categoryId != null) {
            category = menuService.getCategoryById(categoryId);
        }

        return menuService.getMenuItemsForCustomerByHotel(hotelId, category);
    }


    @GetMapping("/admin/{hotelId}")
    public List<MenuItem> getMenuForAdmin(
            @PathVariable Long hotelId,
            @RequestParam(required = false) Long categoryId) {

        Category category = null;
        if (categoryId != null) {
            category = menuService.getCategoryById(categoryId);
        }

        return menuService.getMenuItemsForAdminByHotel(hotelId, category);
    }


    @PutMapping("/disable/{id}")
    public String disable(@PathVariable Long id) {
        menuService.disableMenuItem(id);
        return "Menu disabled";
    }


    @PutMapping("/enable/{id}")
    public String enable(@PathVariable Long id) {
        menuService.enableMenuItem(id);
        return "Menu enabled";
    }
}
