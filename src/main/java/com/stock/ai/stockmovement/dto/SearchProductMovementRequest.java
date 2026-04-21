package com.stock.ai.stockmovement.dto;

import com.stock.ai.stockmovement.enums.MovementType;
import com.stock.ai.stockmovement.enums.SourceType;
import jakarta.validation.constraints.PastOrPresent;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProductMovementRequest {
    private UUID productPublicId;
    private MovementType movementType;
    private SourceType sourceType;

    @PastOrPresent
    private OffsetDateTime fromDate;

    @PastOrPresent
    private OffsetDateTime toDate;
}

