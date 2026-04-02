CREATE DATABASE siam_db;

USE siam_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL,
    registration VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    user_type ENUM('ADMIN', 'INSPECTOR') NOT NULL
);

CREATE TABLE gas_station (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    corporate_name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE gas_pumo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gas_station_id INT NOT NULL,
    serial_number VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    CONSTRAINT fk_pump_station
        FOREIGN KEY (gas_station_id)
        REFERENCES gas_station(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE fiscalization (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    gas_pump_id INT NOT NULL,
    fiscalization_date DATETIME NOT NULL,
    irregularity_type VARCHAR(100),
    measurement_error DECIMAL(10,2),
    audit_status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_fiscalization_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_fiscalization_pump
        FOREIGN KEY (gas_pump_id)
        REFERENCES gas_pump(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE next_steps (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fiscalization_id INT NOT NULL,
    action_description TEXT NOT NULL,
    concluded BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_next_steps_fiscalization
        FOREIGN KEY (fiscalization_id)
        REFERENCES fiscalization(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE photo_evidence (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fiscalization_id INT NOT NULL,
    path_archive VARCHAR(255) NOT NULL,
    CONSTRAINT fk_photo_fiscalization
        FOREIGN KEY (fiscalization_id)
        REFERENCES fiscalization(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO users (name, login, registration, password_hash, user_type)
    VALUES ('Noel Bento', 'noelb@ipemsp.gov.br', 'sp_noelb', '$2a$10$3VUFvUZAsghH3eUjt7yY4O7TDs4f6CsaQl0I1UDBuylazUXJvjHe.', 'ADMIN');

INSERT INTO users (name, login, registration, password_hash, user_type)
    VALUES ('Fiscal Teste', 'fiscalteste@ipemsp.gov.br', 'sp_fiscal', '$2a$10$3VUFvUZAsghH3eUjt7yY4O7TDs4f6CsaQl0I1UDBuylazUXJvjHe.', 'FISCAL');
