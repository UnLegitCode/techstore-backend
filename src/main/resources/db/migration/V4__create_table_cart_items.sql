CREATE TABLE cart_items
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER   NOT NULL,
    product_id INTEGER   NOT NULL,
    quantity   INTEGER   NOT NULL DEFAULT 1,
    added_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cart_items_user
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_cart_items_product
        FOREIGN KEY (product_id) REFERENCES products (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_cart_items_user_product UNIQUE (user_id, product_id),

    CONSTRAINT chk_cart_items_quantity
        CHECK (quantity > 0)
);

CREATE INDEX idx_cart_items_user_id ON cart_items (user_id);