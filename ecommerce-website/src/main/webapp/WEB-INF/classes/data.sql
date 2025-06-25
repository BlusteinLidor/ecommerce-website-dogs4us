-- Ensure the 'users' table exists
CREATE TABLE IF NOT EXISTS users
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(255)        NOT NULL,
    email           VARCHAR(255)        NOT NULL UNIQUE,
    password_hash   VARCHAR(255)        NOT NULL,
    is_admin        BOOLEAN            DEFAULT FALSE
    );

-- Ensure the 'orders' table exists
CREATE TABLE IF NOT EXISTS orders
(
    order_id        UUID PRIMARY KEY,
    order_date      DATE              NOT NULL,
    total_amount    DOUBLE PRECISION  NOT NULL,
    user_id         UUID              NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS category
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
    );

-- Create products table
CREATE TABLE IF NOT EXISTS product
(
    id             UUID PRIMARY KEY,
    name           VARCHAR(255)    NOT NULL,
    description    VARCHAR(1000),
    price          DOUBLE PRECISION NOT NULL,
    category_id    UUID,
    stock_quantity INTEGER,
    FOREIGN KEY (category_id) REFERENCES category (id)
    );

-- Create product_custom_fields table
CREATE TABLE IF NOT EXISTS product_custom_fields
(
    product_id UUID         NOT NULL,
    field_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)
    );

-- Create cart_item table
CREATE TABLE IF NOT EXISTS cart_item
(
    user_id              UUID         NOT NULL,
    product_id          UUID         NOT NULL,
    quantity            INTEGER      NOT NULL,
    customization_preview VARCHAR(255),
    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
    );

-- Create order_item table
CREATE TABLE IF NOT EXISTS order_item
(
    order_id               UUID            NOT NULL,
    product_id            UUID            NOT NULL,
    quantity              INTEGER         NOT NULL,
    unit_price           DOUBLE PRECISION NOT NULL,
    customization_reference VARCHAR(255),
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (product_id) REFERENCES product (id)
    );

-- Create customization table
CREATE TABLE IF NOT EXISTS customization
(
    id          UUID PRIMARY KEY,
    product_id  UUID         NOT NULL,
    order_id    UUID         NOT NULL,
    field_name  VARCHAR(255) NOT NULL UNIQUE,
    field_value VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
    );


-- Insert sample users
INSERT INTO users (id, name, email, password_hash, is_admin)
SELECT '123e4567-e89b-12d3-a456-426614174000',
       'John Doe',
       'john.doe@example.com',
       '$2a$10$abcdefghijklmnopqrstuvwxyz123456789',
       true
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO users (id, name, email, password_hash, is_admin)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       'Jane Smith',
       'jane.smith@example.com',
       '$2a$10$abcdefghijklmnopqrstuvwxyz987654321',
       false
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO users (id, name, email, password_hash, is_admin)
SELECT '123e4567-e89b-12d3-a456-426614174002',
       'Bob Wilson',
       'bob.wilson@example.com',
       '$2a$10$abcdefghijklmnopqrstuvwxyz456789123',
       false
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = '123e4567-e89b-12d3-a456-426614174002');

-- Insert sample orders
INSERT INTO orders (order_id, order_date, total_amount, user_id)
SELECT '223e4567-e89b-12d3-a456-426614174000',
       '2025-06-10',
       299.99,
       '123e4567-e89b-12d3-a456-426614174000'
    WHERE NOT EXISTS (SELECT 1 FROM orders WHERE order_id = '223e4567-e89b-12d3-a456-426614174000');

INSERT INTO orders (order_id, order_date, total_amount, user_id)
SELECT '223e4567-e89b-12d3-a456-426614174001',
       '2025-06-11',
       149.50,
       '123e4567-e89b-12d3-a456-426614174001'
    WHERE NOT EXISTS (SELECT 1 FROM orders WHERE order_id = '223e4567-e89b-12d3-a456-426614174001');

INSERT INTO orders (order_id, order_date, total_amount, user_id)
SELECT '223e4567-e89b-12d3-a456-426614174002',
       '2025-06-09',
       599.99,
       '123e4567-e89b-12d3-a456-426614174002'
    WHERE NOT EXISTS (SELECT 1 FROM orders WHERE order_id = '223e4567-e89b-12d3-a456-426614174002');

INSERT INTO orders (order_id, order_date, total_amount, user_id)
SELECT '223e4567-e89b-12d3-a456-426614174003',
       '2025-06-11',
       89.99,
       '123e4567-e89b-12d3-a456-426614174000'
    WHERE NOT EXISTS (SELECT 1 FROM orders WHERE order_id = '223e4567-e89b-12d3-a456-426614174003');

INSERT INTO category (id, name)
SELECT '323e4567-e89b-12d3-a456-426614174000', 'Dog Food'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = '323e4567-e89b-12d3-a456-426614174000');

INSERT INTO category (id, name)
SELECT '323e4567-e89b-12d3-a456-426614174001', 'Dog Toys'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE id = '323e4567-e89b-12d3-a456-426614174001');

-- Insert sample products
INSERT INTO product (id, name, description, price, category_id, stock_quantity)
SELECT '423e4567-e89b-12d3-a456-426614174000',
       'Premium Dog Food',
       'High-quality dog food for all breeds',
       49.99,
       '323e4567-e89b-12d3-a456-426614174000',
       100
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE id = '423e4567-e89b-12d3-a456-426614174000');

INSERT INTO product (id, name, description, price, category_id, stock_quantity)
SELECT '423e4567-e89b-12d3-a456-426614174001',
       'Customizable Dog Toy',
       'Personalized toy for your furry friend',
       29.99,
       '323e4567-e89b-12d3-a456-426614174001',
       50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE id = '423e4567-e89b-12d3-a456-426614174001');

-- Insert product custom fields
INSERT INTO product_custom_fields (product_id, field_name)
SELECT '423e4567-e89b-12d3-a456-426614174001', 'Color'
    WHERE NOT EXISTS (SELECT 1 FROM product_custom_fields WHERE product_id = '423e4567-e89b-12d3-a456-426614174001' AND field_name = 'Color');

INSERT INTO product_custom_fields (product_id, field_name)
SELECT '423e4567-e89b-12d3-a456-426614174001', 'Size'
    WHERE NOT EXISTS (SELECT 1 FROM product_custom_fields WHERE product_id = '423e4567-e89b-12d3-a456-426614174001' AND field_name = 'Size');

-- Insert cart items
INSERT INTO cart_item (user_id, product_id, quantity, customization_preview)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       '423e4567-e89b-12d3-a456-426614174000',
       2,
       'Standard package'
    WHERE NOT EXISTS (SELECT 1 FROM cart_item WHERE user_id = '123e4567-e89b-12d3-a456-426614174001' AND product_id = '423e4567-e89b-12d3-a456-426614174000');

-- Insert order items
INSERT INTO order_item (order_id, product_id, quantity, unit_price, customization_reference)
SELECT '223e4567-e89b-12d3-a456-426614174000',
       '423e4567-e89b-12d3-a456-426614174000',
       2,
       49.99,
       'CUSTOM-REF-001'
    WHERE NOT EXISTS (SELECT 1 FROM order_item WHERE order_id = '223e4567-e89b-12d3-a456-426614174000' AND product_id = '423e4567-e89b-12d3-a456-426614174000');

-- Insert customizations
INSERT INTO customization (id, product_id, order_id, field_name, field_value)
SELECT '523e4567-e89b-12d3-a456-426614174000',
       '423e4567-e89b-12d3-a456-426614174001',
       '223e4567-e89b-12d3-a456-426614174000',
       'Color Choice',
       'Blue'
    WHERE NOT EXISTS (SELECT 1 FROM customization WHERE id = '523e4567-e89b-12d3-a456-426614174000');
