CREATE TABLE IF NOT EXISTS patient
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    first_name   VARCHAR(255) NOT NULL,
    birth_date   DATE         NOT NULL,
    gender       CHAR(1)      NOT NULL CHECK (gender in ('M', 'F')),
    address      VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20)  NOT NULL UNIQUE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE patient
    ADD CONSTRAINT uq_patient_identity UNIQUE (name, first_name, birth_date);