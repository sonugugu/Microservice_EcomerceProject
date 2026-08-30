package com.ecom.product_service.controller;

import com.ecom.product_service.dto.BulkImportResponse;
import com.ecom.product_service.service.BulkImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
public class BulkImportController {

    private final BulkImportService bulkImportService;

    @PostMapping
    public ResponseEntity<BulkImportResponse> importData(
            @RequestParam("file") MultipartFile file) {
        //Take the uploaded file from the request field named file and store it in the Java variable file
        BulkImportResponse response =
                bulkImportService.importData(file);

        return ResponseEntity.ok(response);
    }
}