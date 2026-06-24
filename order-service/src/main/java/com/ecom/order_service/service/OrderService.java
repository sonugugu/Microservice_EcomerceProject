package com.ecom.order_service.service;


import com.ecom.order_service.dto.*;
import com.ecom.order_service.entity.Orders;
import com.ecom.order_service.entity.OrderItem;
import com.ecom.order_service.events.OrderCreatedEvent;
import com.ecom.order_service.repository.OrderItemRepository;
import com.ecom.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;



    public OrderResponseDTO placeOrder(OrderRequestDTO requestDTO) {
        String orderId = generateOrderId();
        double totalAmount=0.0;
        List<OrderItem>orderItems=new ArrayList<>();
        for (OrderItemRequestDTO itemRequest : requestDTO.getItems()) {
            ProductResposeDTO product = productClient.getProductName(itemRequest.getProductId());
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insuficient Stock" + product.getName());
            }
            productClient.updateStock(itemRequest.getProductId(),-itemRequest.getQuantity());
            double itemTotal=itemRequest.getQuantity()*product.getPrice();
            totalAmount+=itemTotal;
            OrderItem orderItem=new OrderItem(generateOrderItemId(),orderId,
                    product.getProductId(),product.getPrice(),itemRequest.getQuantity());
            orderItems.add(orderItem);
        }
        Orders order=new Orders(orderId,requestDTO.getCustomerId(), LocalDateTime.now(),totalAmount, OrderStatus.PENDING);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        //for Kafka events
        placeOrder(order);
        return new OrderResponseDTO(order.getOrderId(),order.getCustomerId(),
                order.getOrderDate(),order.getTotalAmount(),order.getStatus(),orderItems);
    }
 /*
 public OrderResponseDTO placeOrder(OrderRequestDTO requestDTO) {

    String orderId = generateOrderId();
    double totalAmount = 0.0;
    List<OrderItem> orderItems = new ArrayList<>();

    // Step 1: Validate + Prepare order items
    for (OrderItemRequestDTO itemRequest : requestDTO.getItems()) {

        ProductResposeDTO product = productClient.getProductName(itemRequest.getProductId());

        if (product.getStockQuantity() < itemRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stock for " + product.getName());
        }

        double itemTotal = itemRequest.getQuantity() * product.getPrice();
        totalAmount += itemTotal;

        OrderItem orderItem = new OrderItem(
                generateOrderItemId(),
                orderId,
                product.getProductId(),
                product.getPrice(),
                itemRequest.getQuantity()

        );

        orderItems.add(orderItem);
    }

    // Step 2: Save order in DB
    Orders order = new Orders(
            orderId,
            requestDTO.getCustomerId(),
            LocalDateTime.now(),
            totalAmount,
            OrderStatus.PENDING
    );

    orderRepository.save(order);
    orderItemRepository.saveAll(orderItems);

    // Step 3: Update stock (AFTER DB success ✅)
    for (OrderItemRequestDTO itemRequest : requestDTO.getItems()) {
        productClient.updateStock(
                itemRequest.getProductId(),
                -itemRequest.getQuantity()
        );
    }

    return new OrderResponseDTO(
            order.getOrderId(),
            order.getCustomerId(),
            order.getOrderDate(),
            order.getTotalAmount(),
            order.getStatus(),
            orderItems
    );
}
*/

    public OrderResponseDTO getOrderById(String orderId){
        Orders order =orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found for with ID "+orderId));
        List<OrderItem> items =orderItemRepository.findByOrderId(orderId);
        return new OrderResponseDTO(order.getOrderId(),order.getCustomerId(),
                order.getOrderDate(),order.getTotalAmount(),order.getStatus(),items);
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(String customerId){
        List<Orders> orders=orderRepository.findByCustomerId(customerId);
        List<OrderResponseDTO> responseList=new ArrayList<>();
        for(Orders order:orders)
        {
            List<OrderItem> items=orderItemRepository.findByOrderId(order.getOrderId());
            responseList.add(new OrderResponseDTO(order.getOrderId(),order.getCustomerId(),
                    order.getOrderDate(),order.getTotalAmount(),order.getStatus(),items));
        }
        return responseList;

    }
    public void updateOrderStatus(String orderId, OrderStatus orderStatus) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setStatus(orderStatus);
        orderRepository.save(order);
    }
    // for Kafka
    public void placeOrder(Orders order) {
        try {
            OrderCreatedEvent event = new OrderCreatedEvent(
                    order.getOrderId(),
                    order.getCustomerId(),
                    String.valueOf(order.getTotalAmount())
            );

            log.info("Sending order created event to Kafka for order ID: {}", order.getOrderId());
            kafkaTemplate.send("order-events", event);
            log.info("OrderCreatedEvent sent to kafka for Order id: {}",order.getOrderId());

        } catch (Exception e) {
            log.error("Failed to send order created event to Kafka for order ID: {}", order.getOrderId(), e);
            throw new RuntimeException("Failed to send order created event", e);
        }
    }

    public String generateOrderId() {
        return "ord-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String generateOrderItemId() {
        return "item-" + UUID.randomUUID().toString().substring(0, 8);
    }
}

