package com.ecom.order_service.controller;

import com.ecom.order_service.dto.OrderRequestDTO;
import com.ecom.order_service.dto.OrderResponseDTO;
import com.ecom.order_service.dto.OrderStatus;
import com.ecom.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService)
    {
        this.orderService=orderService;
    }
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO responseDTO = orderService.placeOrder(request);
       // return ResponseEntity.ok(responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String orderId) {
        OrderResponseDTO orderResponse = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status)
    {
        orderService.updateOrderStatus(orderId,status);
        return ResponseEntity.ok("Order status updated to"+ status.name());
    }
}
