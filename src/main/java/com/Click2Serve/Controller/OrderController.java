package com.Click2Serve.Controller;

import com.Click2Serve.Dto.OrderDTO;
import com.Click2Serve.Entity.Order;
import com.Click2Serve.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.placeOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId) {
        orderService.confirmOrder(orderId);
        return ResponseEntity.ok("Order confirmed");
    }
}
