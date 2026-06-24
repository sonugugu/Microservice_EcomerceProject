package com.ecom.payment_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private String paymentId;
    private String orderId;
    private String customerId;
    private String amount;
    //private String paymentDate;
    private PaymentStatus paymentStatus;
    private String transactionId;

}
