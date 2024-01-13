CREATE TABLE inventory (
    id CHAR(36) NOT NULL,
    code_sku VARCHAR(30) UNIQUE NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (id)
);
