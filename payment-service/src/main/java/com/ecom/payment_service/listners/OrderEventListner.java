package com.ecom.payment_service.listners;

import com.ecom.payment_service.dto.PaymentRequestDTO;
import com.ecom.payment_service.events.OrderCreatedEvent;
import com.ecom.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor // this use inject via contructor, no need to write Autowire or constructor
public class OrderEventListner {
    private final PaymentService paymentService;

    @KafkaListener(topics = "order-events",groupId  ="payment-service-group")
    public void consume(OrderCreatedEvent orderCreatedEvent) {
        try {
        log.info("OrderCreatedEvent received: {}", orderCreatedEvent);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setOrderId(orderCreatedEvent.getOrderId());
        paymentRequestDTO.setCustomerId(orderCreatedEvent.getCustomerId());
        paymentRequestDTO.setAmount(orderCreatedEvent.getTotalAmount());

        paymentService.processPayment(paymentRequestDTO);
        } catch (Exception e) {
            log.error("Error processing OrderCreatedEvent: {}", e.getMessage());
            throw new RuntimeException("Error processing OrderCreatedEvent", e);
        }
    }

}
