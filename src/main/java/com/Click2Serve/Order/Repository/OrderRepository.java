package com.Click2Serve.Order.Repository;


import com.Click2Serve.Order.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByRoomId(Long roomId);
}

