package com.ecom.order_service.service;

import com.ecom.order_service.dto.ProductResposeDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public ProductResposeDTO getProductName(String productId){
        String url ="http://product-service/products/"+productId;
        return restTemplate.getForObject(url,ProductResposeDTO.class);
    }
    public void updateStock(String productId, Integer quantity){
      /* // String url ="http://localhost:6000/products/"+productId+"stock?stockQuantity="+quantity;
        String url ="http://localhost:6000/products/"+productId+"/stock?stockQuantity="+quantity;
         restTemplate.patchForObject(url, null,Void.class);  */
        String url ="http://product-service/products/"+productId+"/stock?stockQuantity="+quantity;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        restTemplate.exchange(url, HttpMethod.PATCH, entity, Void.class);
    }
}
