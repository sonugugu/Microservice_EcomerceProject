package com.ecom.product_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponseDto {

    private String path;
    private Object status;
    private String message;
    private LocalDateTime timestamp;

}
