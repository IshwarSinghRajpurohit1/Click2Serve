package com.Click2Serve.Order.Repository;

import com.Click2Serve.Order.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
