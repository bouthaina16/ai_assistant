package com.stock.ai.stockmovement.controller;

import com.stock.ai.stockmovement.dto.ProductMovementRequest;
import com.stock.ai.stockmovement.dto.ProductMovementResponse;
import com.stock.ai.stockmovement.dto.SearchProductMovementRequest;
import com.stock.ai.stockmovement.service.ProductMovementService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock-movements")
public class ProductMovementController {

    private final ProductMovementService productMovementService;

    public ProductMovementController(ProductMovementService productMovementService) {
        this.productMovementService = productMovementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductMovementResponse createMovement(@Valid @RequestBody ProductMovementRequest request) {
        return productMovementService.createMovement(request);
    }

    @GetMapping
    public List<ProductMovementResponse> getAllMovements() {
        return productMovementService.getAllMovements();
    }

    @GetMapping("/{publicId}")
    public ProductMovementResponse getMovementByPublicId(@PathVariable String publicId) {
        return productMovementService.getMovementByPublicId(publicId);
    }

    @GetMapping("/product/{productPublicId}")
    public List<ProductMovementResponse> getMovementsByProduct(@PathVariable String productPublicId) {
        return productMovementService.getMovementsByProduct(productPublicId);
    }

    @PostMapping("/search")
    public List<ProductMovementResponse> searchMovements(@Valid @RequestBody SearchProductMovementRequest request) {
        return productMovementService.searchMovements(request);
    }
}

