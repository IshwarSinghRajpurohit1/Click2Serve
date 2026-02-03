package com.Click2Serve.Controller;
import com.Click2Serve.Dto.CategoryMenuDTO;
import com.Click2Serve.Dto.MenuResponseDTO;
import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.MenueItem;
import com.Click2Serve.service.MenuService;
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
    public MenuResponseDTO addMenu(@RequestBody MenueItem item) {


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
    public List<MenueItem> getMenuForCustomer(
            @PathVariable Long hotelId,
            @RequestParam(required = false) Long categoryId) {

        Category category = null;
        if (categoryId != null) {
            category = menuService.getCategoryById(categoryId);
        }

        return menuService.getMenuItemsForCustomerByHotel(hotelId, category);
    }


    @GetMapping("/admin/{hotelId}")
    public List<MenueItem> getMenuForAdmin(
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
