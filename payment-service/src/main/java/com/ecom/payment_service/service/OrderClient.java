package com.ecom.payment_service.service;

import com.ecom.payment_service.dto.OrderStatusUpdateRequestDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderClient {
    private final RestTemplate restTemplate;

    public OrderClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public void updateOrderStatus(String orderId, String status){

        String url = "http://localhost:6001/orders/"
                + orderId + "/status?status=" + status;

        restTemplate.put(url, null);   // ✅ FIX

        System.out.println("Order status updated successfully");
    }

    /*
    private final RestTemplate restTemplate;
    public OrderClient(RestTemplateBuilder builder){
        this.restTemplate=builder.build();
    }
    public void updateOrderStatus(String orderId, String status){
        // localhost:6001/orders/ord-26971126/status?status=CONFIRMED
        String url="http://localhost:6001/orders/"+orderId+"status?status="+status;
        OrderStatusUpdateRequestDTO request =new OrderStatusUpdateRequestDTO(orderId,status);
        restTemplate.postForObject(url,request,Void.class);
    } */
}
