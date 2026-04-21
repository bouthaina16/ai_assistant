package com.stock.ai.stockmovement.service;

import com.stock.ai.stockmovement.dto.ProductMovementRequest;
import com.stock.ai.stockmovement.dto.ProductMovementResponse;
import com.stock.ai.stockmovement.dto.SearchProductMovementRequest;
import com.stock.ai.stockmovement.entity.ProductMovement;
import com.stock.ai.stockmovement.mapper.ProductMovementMapper;
import com.stock.ai.stockmovement.repository.ProductMovementRepository;
import com.stock.ai.stockmovement.service.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductMovementServiceImpl implements ProductMovementService {

    private final ProductMovementRepository productMovementRepository;
    private final ProductMovementMapper productMovementMapper;

    public ProductMovementServiceImpl(
            ProductMovementRepository productMovementRepository,
            ProductMovementMapper productMovementMapper
    ) {
        this.productMovementRepository = productMovementRepository;
        this.productMovementMapper = productMovementMapper;
    }

    @Override
    @Transactional
    public ProductMovementResponse createMovement(ProductMovementRequest request) {
        ProductMovement movement = productMovementMapper.toEntity(request);
        ProductMovement saved = productMovementRepository.save(movement);
        return productMovementMapper.toResponse(saved);
    }

    @Override
    public List<ProductMovementResponse> getAllMovements() {
        return productMovementRepository.findAll(Sort.by(Sort.Direction.DESC, "movementDate"))
                .stream()
                .map(productMovementMapper::toResponse)
                .toList();
    }

    @Override
    public ProductMovementResponse getMovementByPublicId(String publicId) {
        ProductMovement movement = productMovementRepository.findByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new ResourceNotFoundException("Product movement not found for publicId=" + publicId));

        return productMovementMapper.toResponse(movement);
    }

    @Override
    public List<ProductMovementResponse> getMovementsByProduct(String productPublicId) {
        return productMovementRepository.findByProductPublicIdOrderByMovementDateDesc(UUID.fromString(productPublicId))
                .stream()
                .map(productMovementMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductMovementResponse> searchMovements(SearchProductMovementRequest request) {
        Specification<ProductMovement> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getProductPublicId() != null) {
                predicates.add(cb.equal(root.get("productPublicId"), request.getProductPublicId()));
            }
            if (request.getMovementType() != null) {
                predicates.add(cb.equal(root.get("movementType"), request.getMovementType()));
            }
            if (request.getSourceType() != null) {
                predicates.add(cb.equal(root.get("sourceType"), request.getSourceType()));
            }
            if (request.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("movementDate"), request.getFromDate()));
            }
            if (request.getToDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("movementDate"), request.getToDate()));
            }

            query.orderBy(cb.desc(root.get("movementDate")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productMovementRepository.findAll(specification)
                .stream()
                .map(productMovementMapper::toResponse)
                .toList();
    }
}

