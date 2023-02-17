CREATE TABLE player
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    age         NUMERIC(10,2),
    club        VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL
);
