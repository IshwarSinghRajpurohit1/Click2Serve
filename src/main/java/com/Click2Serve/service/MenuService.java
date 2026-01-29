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

        // ❌ Galti: Duplicate methods + status field
        // ✅ Fix: Remove getMenuItemsByCategory(), remove status, keep only active flag

        // 👤 CUSTOMER – active menu items only
        public List<MenueItem> getMenuItemsForCustomer(Category category) {
            return menuItemRepository.findByCategoryAndActiveTrue(category);
        }

        // 🔐 ADMIN – all menu items
        public List<MenueItem> getMenuItemsForAdmin(Category category) {
            return menuItemRepository.findByCategory(category);
        }

        // ➕ Add menu item
        public MenueItem addMenuItem(MenueItem menuItem) {
            menuItem.setActive(true); // default enabled
            return menuItemRepository.save(menuItem);
        }

        // ❌ DISABLE/ENABLE menu items
        public void disableMenuItem(Long menuItemId) {
            MenueItem item = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));
            item.setActive(false);
            menuItemRepository.save(item);
        }

        public void enableMenuItem(Long menuItemId) {
            MenueItem item = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));
            item.setActive(true);
            menuItemRepository.save(item);
        }

        // ✅ Category enable/disable
        public void disableCategory(Long categoryId) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            category.setActive(false);
            categoryRepository.save(category);
        }

        public void enableCategory(Long categoryId) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            category.setActive(true);
            categoryRepository.save(category);
        }

        // 👤 CUSTOMER – active categories
        public List<Category> getActiveCategories() {
            return categoryRepository.findByActiveTrue();
        }

        // 🔐 ADMIN – all categories
        public List<Category> getAllCategories() {
            return categoryRepository.findAll();
        }

        // 🔐 Optional: get category by ID
        public Category getCategoryById(Long id) {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }
    }
