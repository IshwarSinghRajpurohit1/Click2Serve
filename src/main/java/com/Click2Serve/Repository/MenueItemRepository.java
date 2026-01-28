package com.Click2Serve.Repository;

import com.Click2Serve.Entity.MenueItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenueItemRepository extends JpaRepository<MenueItem,Long> {
}
