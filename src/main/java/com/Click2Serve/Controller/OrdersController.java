package com.Click2Serve.Controller;



import com.Click2Serve.Dto.OrderRequestDTO;
import com.Click2Serve.Dto.OrderResponseDTO;
import com.Click2Serve.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    // 1. Customer: Naya order place karne ke liye
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO response = orderService.placeOrder(request);
        return ResponseEntity.ok(response);
    }

    // 2. Admin: Order ka status change karne ke liye (PENDING -> SERVED -> COMPLETED)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        orderService.updateStatus(orderId, status);
        return ResponseEntity.ok("Order status updated to: " + status);
    }

    // 3. Admin: Ek hotel ke saare orders dekhne ke liye
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<?>> getOrdersByHotel(@PathVariable Long hotelId) {
        // Aap OrderService mein ye method add kar sakte hain saare orders nikalne ke liye
        return ResponseEntity.ok(orderService.getOrdersByHotel(hotelId));
    }
}