CREATE DATABASE siam_db;

USE siam_db;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    login VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    user_type ENUM('ADMIN', 'FISCAL') NOT NULL
);

INSERT INTO user (name, login, password_hash, user_type)
VALUES ('Admin', 'admin', '123456', 'ADMIN');