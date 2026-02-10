package com.Click2Serve.Order.Controller;

import com.Click2Serve.Order.DTO.OrderCreateDTO;

import com.Click2Serve.Order.DTO.OrderResponseDTO;
import com.Click2Serve.Order.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> placeOrder(@RequestBody OrderCreateDTO dto) {
        ResponseEntity<Map<String, Object>> order = orderService.placeOrder(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }


    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId) {
        orderService.confirmOrder(orderId);
        return ResponseEntity.ok("Order confirmed");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(orderService.getOrdersByRoom(roomId));
    }

}
