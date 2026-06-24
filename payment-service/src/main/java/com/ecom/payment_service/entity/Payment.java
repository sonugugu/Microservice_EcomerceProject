package com.ecom.payment_service.entity;

import com.ecom.payment_service.dto.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class Payment {

    @Id
    private String paymentId;

    private String orderId;
    private String customerId;
    private String amount;
    private LocalDateTime paymentDate;
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
