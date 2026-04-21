package com.stock.ai.stockmovement.entity;

import com.stock.ai.stockmovement.enums.MovementType;
import com.stock.ai.stockmovement.enums.SourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "product_movement",
        indexes = {
                @Index(name = "idx_product_movement_public_id", columnList = "public_id", unique = true),
                @Index(name = "idx_product_movement_product_public_id", columnList = "product_public_id"),
                @Index(name = "idx_product_movement_movement_type", columnList = "movement_type"),
                @Index(name = "idx_product_movement_source_type", columnList = "source_type"),
                @Index(name = "idx_product_movement_date", columnList = "movement_date")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "product_public_id", nullable = false)
    private UUID productPublicId;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 32)
    private MovementType movementType;

    @Column(name = "quantity", nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(name = "old_stock", nullable = false, precision = 19, scale = 4)
    private BigDecimal oldStock;

    @Column(name = "new_stock", nullable = false, precision = 19, scale = 4)
    private BigDecimal newStock;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 32)
    private SourceType sourceType;

    @Column(name = "source_reference", length = 120)
    private String sourceReference;

    @Column(name = "movement_date", nullable = false)
    private OffsetDateTime movementDate;

    @Column(name = "comment", length = 500)
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}

