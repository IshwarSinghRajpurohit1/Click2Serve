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

        @PostMapping
        public MenueItem addMenuItem(@RequestBody MenueItem menuItem) {
            return menuService.addMenuItem(menuItem);
        }

        @GetMapping("/category/{categoryId}")
        public List<MenueItem> getMenuByCategory(@PathVariable Long categoryId) {
            // fetch category object first
            Category category = new Category();
            category.setId(categoryId);
            return menuService.getMenuItemsByCategory(category);
        }
    }




