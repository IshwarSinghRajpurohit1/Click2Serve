package com.Click2Serve.Controller;

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

    // ➕ Add menu item (category optional)
    @PostMapping("/add")
    public MenueItem addMenu(@RequestBody MenueItem item) {
        // Agar category null hai to ignore
        if (item.getCategory() != null && item.getCategory().getId() != null) {
            Category category = menuService.getCategoryById(item.getCategory().getId());
            item.setCategory(category);
        } else {
            item.setCategory(null);
        }

        // Hotel must be provided
        if (item.getHotel() == null || item.getHotel().getId() == null) {
            throw new RuntimeException("Hotel ID is required");
        } else {
            // Set hotel reference
            item.setHotel(item.getHotel());
        }

        // Default active true
        if (item.getActive() == null) {
            item.setActive(true);
        }

        return menuService.addMenuItem(item);
    }

    // 👤 Customer: hotel + optional category
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

    // 🔐 Admin: hotel + optional category
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

    // ❌ Disable menu item
    @PutMapping("/disable/{id}")
    public String disable(@PathVariable Long id) {
        menuService.disableMenuItem(id);
        return "Menu disabled";
    }

    // ✅ Enable menu item
    @PutMapping("/enable/{id}")
    public String enable(@PathVariable Long id) {
        menuService.enableMenuItem(id);
        return "Menu enabled";
    }
}
