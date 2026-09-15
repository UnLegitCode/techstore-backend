CREATE TABLE products
(
    id             SERIAL PRIMARY KEY,
    title          VARCHAR(255)   NOT NULL,
    category_id    INTEGER        NOT NULL,
    price          DECIMAL(10, 2) NOT NULL,
    previous_price DECIMAL(10, 2),
    rating         DECIMAL(2, 1)  NOT NULL,
    reviews        INTEGER        NOT NULL DEFAULT 0,
    emoji          VARCHAR(16)    NOT NULL,
    badge          VARCHAR(50),

    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES product_categories (id)
            ON DELETE RESTRICT,

    CONSTRAINT chk_products_rating
        CHECK (rating >= 1.0 AND rating <= 5.0),

    CONSTRAINT chk_products_price
        CHECK (price >= 0),

    CONSTRAINT chk_products_reviews
        CHECK (reviews >= 0)
);

CREATE INDEX idx_products_category_id ON products (category_id);