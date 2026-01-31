package com.Click2Serve.service;

import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.MenueItem;
import com.Click2Serve.Repository.CategoryRepository;
import com.Click2Serve.Repository.MenueItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenueItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    public MenuService(MenueItemRepository menuItemRepository,
                       CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    // ==========================
    // Customer: active items, category optional
    // ==========================
    public List<MenueItem> getMenuItemsForCustomerByHotel(Long hotelId, Category category) {
        if (category != null) {
            // Category provided → filter by category
            return menuItemRepository.findByHotel_IdAndCategoryAndActiveTrue(hotelId, category);
        } else {
            // Category not provided → return all active items for hotel
            return menuItemRepository.findByHotel_IdAndActiveTrue(hotelId);
        }
    }

    // ==========================
    // Admin: all items, category optional
    // ==========================
    public List<MenueItem> getMenuItemsForAdminByHotel(Long hotelId, Category category) {
        if (category != null) {
            // Category provided → filter by category
            return menuItemRepository.findByHotel_IdAndCategory(hotelId, category);
        } else {
            // Category not provided → return all items for hotel
            return menuItemRepository.findByHotel_Id(hotelId);
        }
    }

    // ==========================
    // Add new menu item
    // ==========================
    public MenueItem addMenuItem(MenueItem menuItem) {
        // Default active true
        if (menuItem.getActive() == null) {
            menuItem.setActive(true);
        }

        // Category optional
        if (menuItem.getCategory() == null) {
            menuItem.setCategory(null);
        }

        // Hotel mandatory
        if (menuItem.getHotel() == null || menuItem.getHotel().getId() == null) {
            throw new RuntimeException("Hotel ID is required");
        }

        return menuItemRepository.save(menuItem);
    }

    // ==========================
    // Disable menu item
    // ==========================
    public void disableMenuItem(Long id) {
        MenueItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        item.setActive(false);
        menuItemRepository.save(item);
    }

    // ==========================
    // Enable menu item
    // ==========================
    public void enableMenuItem(Long id) {
        MenueItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        item.setActive(true);
        menuItemRepository.save(item);
    }

    // ==========================
    // Get category by ID
    // ==========================
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
