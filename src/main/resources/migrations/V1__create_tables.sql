CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE categories
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    emoji VARCHAR(16)  NOT NULL
);

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
        FOREIGN KEY (category_id) REFERENCES categories (id)
            ON DELETE RESTRICT,

    CONSTRAINT chk_products_rating
        CHECK (rating >= 1.0 AND rating <= 5.0),

    CONSTRAINT chk_products_price
        CHECK (price >= 0),

    CONSTRAINT chk_products_reviews
        CHECK (reviews >= 0)
);

CREATE INDEX idx_products_category_id ON products (category_id);

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