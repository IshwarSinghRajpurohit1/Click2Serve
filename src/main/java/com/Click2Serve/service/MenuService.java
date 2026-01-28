package com.Click2Serve.service;


import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.MenueItem;
import com.Click2Serve.Repository.MenueItemRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class MenuService {

    private final MenueItemRepository menuItemRepository;

    public MenuService(MenueItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenueItem addMenuItem(MenueItem menueItem) {
        menueItem.setStatus("ACTIVE");
        return menuItemRepository.save(menueItem);
    }

    public List<MenueItem> getMenuItemsByCategory(Category category) {
        return menuItemRepository.findByCategory(category);
    }
}
