package com.ecom.product_service.service;

import com.ecom.product_service.dto.BulkImportRequest;
import com.ecom.product_service.dto.BulkImportResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BulkImportService {

    BulkImportResponse importData(
            MultipartFile file
    );
}