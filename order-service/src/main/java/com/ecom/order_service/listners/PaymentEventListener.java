package com.ecom.order_service.listners;

import com.ecom.order_service.dto.OrderStatus;
import com.ecom.order_service.entity.Orders;
import com.ecom.order_service.entity.Orders;
import com.ecom.order_service.events.PaymentCompletedEvent;
import com.ecom.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
// didnt test
public class PaymentEventListener {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "payment-events",
            groupId = "order-group",
            containerFactory = "paymentKafkaListenerContainerFactory"
    )
    public void consume(PaymentCompletedEvent event) {

        log.info("Received payment event for order {}", event.getOrderId());
        log.info("EVENT RECEIVED = {}", event);

        Orders order = orderRepository.findByOrderId(event.getOrderId());
        log.info("ORDER FOUND = {}", order);

        if (order != null) {

            order.setStatus(OrderStatus.valueOf(event.getStatus()));

            orderRepository.save(order);

            log.info("Order status updated to {}", event.getStatus());
        }
    }
}