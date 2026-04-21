package com.stock.ai.stockmovement.dto;

import com.stock.ai.stockmovement.enums.MovementType;
import com.stock.ai.stockmovement.enums.SourceType;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductMovementResponse {
    UUID publicId;
    UUID productPublicId;
    String productName;
    MovementType movementType;
    BigDecimal quantity;
    BigDecimal oldStock;
    BigDecimal newStock;
    SourceType sourceType;
    String sourceReference;
    OffsetDateTime movementDate;
    String comment;
    OffsetDateTime createdAt;
}

