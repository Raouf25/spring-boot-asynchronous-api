CREATE TABLE player
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    age         NUMERIC(10, 2),
    club        VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL
);

create table product
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    code             VARCHAR(255),
    name             VARCHAR(255),
    description      VARCHAR(255),
    price            DOUBLE,
    quantity         INTEGER,
    inventory_status VARCHAR(255),
    category         VARCHAR(255),
    image            VARCHAR(255),
    rating           DOUBLE
);
