CREATE DATABASE if not exists siam_db;

USE siam_db;

CREATE TABLE if not exists user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    login VARCHAR(120) UNIQUE NOT NULL,
    registration VARCHAR(20)   NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    user_type ENUM('ADMIN', 'FISCAL') NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE TABLE gas_station (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             cnpj VARCHAR(18) NOT NULL UNIQUE,
                             corporate_name VARCHAR(150) NOT NULL,
                             address VARCHAR(255) NOT NULL,
                             active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE gas_pump (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          gas_station_id INT NOT NULL,
                          serial_number VARCHAR(100) NOT NULL,
                          model VARCHAR(100) NOT NULL,
                          active BOOLEAN NOT NULL DEFAULT TRUE,
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
                               active BOOLEAN NOT NULL DEFAULT TRUE,
                               archived_at DATETIME DEFAULT NULL,
                               CONSTRAINT fk_fiscalization_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES user(id)
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

INSERT INTO user (name, login, registration, password_hash, user_type)
VALUES (
        'Admin',
        'testeadmin@ipem.sp.gov.br',
        '0000',
        '$2a$10$3VUFvUZAsghH3eUjt7yY4O7TDs4f6CsaQl0I1UDBuylazUXJvjHe.',
        'ADMIN');

-- Usuário Admin login: testeadmin@ipem.sp.gov.br ou 0000 senha: 123456
