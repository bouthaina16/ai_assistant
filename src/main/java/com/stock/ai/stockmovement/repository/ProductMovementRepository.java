package com.stock.ai.stockmovement.repository;

import com.stock.ai.stockmovement.entity.ProductMovement;
import com.stock.ai.stockmovement.enums.MovementType;
import com.stock.ai.stockmovement.enums.SourceType;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductMovementRepository extends JpaRepository<ProductMovement, Long>, JpaSpecificationExecutor<ProductMovement> {

    Optional<ProductMovement> findByPublicId(UUID publicId);

    List<ProductMovement> findByProductPublicIdOrderByMovementDateDesc(UUID productPublicId);

    List<ProductMovement> findByMovementTypeOrderByMovementDateDesc(MovementType movementType);

    List<ProductMovement> findBySourceTypeOrderByMovementDateDesc(SourceType sourceType);

    List<ProductMovement> findByMovementDateBetweenOrderByMovementDateDesc(OffsetDateTime fromDate, OffsetDateTime toDate);
}

