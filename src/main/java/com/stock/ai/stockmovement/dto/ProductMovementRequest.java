package com.stock.ai.stockmovement.dto;

import com.stock.ai.stockmovement.enums.MovementType;
import com.stock.ai.stockmovement.enums.SourceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMovementRequest {

    @NotNull
    private UUID productPublicId;

    @NotBlank
    @Size(max = 255)
    private String productName;

    @NotNull
    private MovementType movementType;

    @NotNull
    @DecimalMin(value = "0.0001", inclusive = true)
    private BigDecimal quantity;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal oldStock;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal newStock;

    @NotNull
    private SourceType sourceType;

    @Size(max = 120)
    private String sourceReference;

    @NotNull
    private OffsetDateTime movementDate;

    @Size(max = 500)
    private String comment;
}

