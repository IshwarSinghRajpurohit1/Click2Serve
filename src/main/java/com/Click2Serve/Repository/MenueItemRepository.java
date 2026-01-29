package com.Click2Serve.Repository;
import  com.Click2Serve.Entity.Category;
import  com.Click2Serve.Entity.MenueItem;
import  org.springframework.data.jpa.repository.JpaRepository;
import  java.util.List;

public interface MenueItemRepository extends JpaRepository<MenueItem, Long> {

    // 🔐 ADMIN – saare items
    List<MenueItem> findByCategory(Category category);

    // 👤 CUSTOMER –  active items
    List<MenueItem> findByCategoryAndActiveTrue(Category category);

}

