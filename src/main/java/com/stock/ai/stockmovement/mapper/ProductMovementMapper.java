package com.stock.ai.stockmovement.mapper;

import com.stock.ai.stockmovement.dto.ProductMovementRequest;
import com.stock.ai.stockmovement.dto.ProductMovementResponse;
import com.stock.ai.stockmovement.entity.ProductMovement;
import org.springframework.stereotype.Component;

@Component
public class ProductMovementMapper {

    public ProductMovement toEntity(ProductMovementRequest request) {
        return ProductMovement.builder()
                .productPublicId(request.getProductPublicId())
                .productName(request.getProductName())
                .movementType(request.getMovementType())
                .quantity(request.getQuantity())
                .oldStock(request.getOldStock())
                .newStock(request.getNewStock())
                .sourceType(request.getSourceType())
                .sourceReference(request.getSourceReference())
                .movementDate(request.getMovementDate())
                .comment(request.getComment())
                .build();
    }

    public ProductMovementResponse toResponse(ProductMovement entity) {
        return ProductMovementResponse.builder()
                .publicId(entity.getPublicId())
                .productPublicId(entity.getProductPublicId())
                .productName(entity.getProductName())
                .movementType(entity.getMovementType())
                .quantity(entity.getQuantity())
                .oldStock(entity.getOldStock())
                .newStock(entity.getNewStock())
                .sourceType(entity.getSourceType())
                .sourceReference(entity.getSourceReference())
                .movementDate(entity.getMovementDate())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

