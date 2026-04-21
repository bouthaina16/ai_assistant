package com.stock.ai.stockmovement.service;

import com.stock.ai.stockmovement.dto.ProductMovementRequest;
import com.stock.ai.stockmovement.dto.ProductMovementResponse;
import com.stock.ai.stockmovement.dto.SearchProductMovementRequest;
import java.util.List;

public interface ProductMovementService {

    ProductMovementResponse createMovement(ProductMovementRequest request);

    List<ProductMovementResponse> getAllMovements();

    ProductMovementResponse getMovementByPublicId(String publicId);

    List<ProductMovementResponse> getMovementsByProduct(String productPublicId);

    List<ProductMovementResponse> searchMovements(SearchProductMovementRequest request);
}

