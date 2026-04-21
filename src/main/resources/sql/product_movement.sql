CREATE TABLE IF NOT EXISTS product_movement (
    id BIGSERIAL PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE,
    product_public_id UUID NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    movement_type VARCHAR(32) NOT NULL,
    quantity NUMERIC(19,4) NOT NULL,
    old_stock NUMERIC(19,4) NOT NULL,
    new_stock NUMERIC(19,4) NOT NULL,
    source_type VARCHAR(32) NOT NULL,
    source_reference VARCHAR(120),
    movement_date TIMESTAMP WITH TIME ZONE NOT NULL,
    comment VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_product_movement_product_public_id
    ON product_movement(product_public_id);

CREATE INDEX IF NOT EXISTS idx_product_movement_movement_type
    ON product_movement(movement_type);

CREATE INDEX IF NOT EXISTS idx_product_movement_source_type
    ON product_movement(source_type);

CREATE INDEX IF NOT EXISTS idx_product_movement_date
    ON product_movement(movement_date);

