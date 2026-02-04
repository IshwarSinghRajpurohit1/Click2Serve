package com.Click2Serve.Controller;

import com.Click2Serve.Dto.OrderCreateDTO;

import com.Click2Serve.Dto.OrderResponseDTO;
import com.Click2Serve.Entity.Order;
import com.Click2Serve.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderCreateDTO dto) {
        Order order = orderService.placeOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }


    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId) {
        orderService.confirmOrder(orderId);
        return ResponseEntity.ok("Order confirmed");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(orderService.getOrdersByRoom(roomId));
    }

}
