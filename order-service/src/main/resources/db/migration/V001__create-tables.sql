CREATE TABLE `orders` (
    order_id CHAR(36) PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL
);

CREATE TABLE `order_line_items` (
    id CHAR(36) PRIMARY KEY,
    code_sku VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INTEGER NOT NULL,
    order_id CHAR(36) REFERENCES `orders`(order_id)
);

