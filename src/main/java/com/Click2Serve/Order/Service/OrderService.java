package com.Click2Serve.Order.Service;

import com.Click2Serve.Exception.ResponseClass;
import com.Click2Serve.Order.DTO.OrderCreateDTO;
import com.Click2Serve.Order.DTO.OrderItemRequestDTO;
import com.Click2Serve.Order.DTO.OrderItemResponseDTO;
import com.Click2Serve.Order.DTO.OrderResponseDTO;
import com.Click2Serve.Menu.Entity.MenuItem;
import com.Click2Serve.Menu.Repository.MenuItemRepository;
import com.Click2Serve.Order.Entity.Order;
import com.Click2Serve.Order.Entity.OrderItem;
import com.Click2Serve.Order.Repository.OrderItemRepository;
import com.Click2Serve.Order.Repository.OrderRepository;
import com.Click2Serve.Room.Entity.Room;
import com.Click2Serve.Room.Repository.RoomRepository;
import com.Click2Serve.Status.OrderStatus;
import com.Click2Serve.Users.Entity.User;
import com.Click2Serve.Users.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menueItemRepository;


    @Transactional
    public ResponseEntity<Map<String, Object>> placeOrder(OrderCreateDTO dto) {

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .room(room)
                .user(user)
                .status(OrderStatus.PENDING)
                .orderTime(LocalDateTime.now())
                .totalAmount(0.0)
                .build();

        order = orderRepository.save(order);

        double grandTotal = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {

            MenuItem menuItem = menueItemRepository.findById(itemDTO.getItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            double itemTotal = menuItem.getPrice() * itemDTO.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setTotalPrice(itemTotal);

            grandTotal += itemTotal;
            orderItems.add(orderItem);
        }

        order.setTotalAmount(grandTotal);
        order.setItems(orderItems);

        orderItemRepository.saveAll(orderItems);
        Order order1 = orderRepository.save(order);
        return ResponseClass.responseSuccess("order placed successfully","order",order1);

    }


    @Transactional
    public void confirmOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only PENDING orders can be confirmed");
        }

        order.setStatus(OrderStatus.CONFIRMED);
             Order order1 =  orderRepository.save(order);
              ResponseClass.responseSuccess("order have been done","Order",order1);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

         OrderResponseDTO order1 = mapToResponse(order);
       return       ResponseClass.responseSuccess("order id get","order",order1);

    }


    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByRoom(Long roomId) {

       OrderResponseDTO dtos = (OrderResponseDTO) orderRepository.findByRoomId(roomId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
       return (List<OrderResponseDTO>) ResponseClass.responseSuccess("orger get","order",dtos);
    }


    private OrderResponseDTO mapToResponse(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setOrderTime(order.getOrderTime());

        dto.setRoomId(order.getRoom().getId());
        dto.setRoomNumber(order.getRoom().getRoomNumber());
        dto.setUserId(order.getUser().getId());


        List<OrderItemResponseDTO> items = order.getItems().stream().map(item -> {
            OrderItemResponseDTO i = new OrderItemResponseDTO();
            i.setItemId(item.getMenuItem().getId());
            i.setItemName(item.getMenuItem().getName());
            i.setQuantity(item.getQuantity());
            i.setPrice(item.getPrice());
            i.setTotalPrice(item.getTotalPrice());
            return i;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }
}
