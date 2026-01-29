package com.Click2Serve.Controller;
import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.MenueItem;
import com.Click2Serve.service.MenuService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

    @RestController
    @RequestMapping("/menu")

    public class MenuController {

        // ❌ Galti: variable name inconsistent
        // private final MenuService menueService;
        // ✅ Fix:
        private final MenuService menuService;

        public MenuController(MenuService menuService) {
            this.menuService = menuService;
        }

        // ❌ Galti: Fake category object create kar raha ho
    /*
    @GetMapping("/category/{categoryId}")
    public List<MenueItem> getMenuByCategory(@PathVariable Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return menuService.getMenuItemsByCategory(category);
    }
    */
        // ✅ Fix: Fetch category from DB and separate customer/admin
        @GetMapping("/customer/category/{categoryId}")
        public List<MenueItem> getMenuForCustomer(@PathVariable Long categoryId) {
            Category category = menuService.getCategoryById(categoryId);
            return menuService.getMenuItemsForCustomer(category);
        }

        @GetMapping("/admin/category/{categoryId}")
        public List<MenueItem> getMenuForAdmin(@PathVariable Long categoryId) {
            Category category = menuService.getCategoryById(categoryId);
            return menuService.getMenuItemsForAdmin(category);
        }

        // ❌ Galti: Only disable exists
        // ✅ Fix: Add enable endpoint
        @PutMapping("/disable/{menuItemId}")
        public String disableMenuItem(@PathVariable Long menuItemId) {
            menuService.disableMenuItem(menuItemId);
            return "Menu item disabled successfully";
        }

        @PutMapping("/enable/{menuItemId}")
        public String enableMenuItem(@PathVariable Long menuItemId) {
            menuService.enableMenuItem(menuItemId);
            return "Menu item enabled successfully";
        }

        // ✅ Add Category enable/disable endpoints
        @PutMapping("/category/disable/{categoryId}")
        public String disableCategory(@PathVariable Long categoryId) {
            menuService.disableCategory(categoryId);
            return "Category disabled successfully";
        }

        @PutMapping("/category/enable/{categoryId}")
        public String enableCategory(@PathVariable Long categoryId) {
            menuService.enableCategory(categoryId);
            return "Category enabled successfully";
        }
    }





