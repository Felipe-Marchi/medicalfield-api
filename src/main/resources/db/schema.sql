-- Remove as tabelas existentes respeitando a ordem das chaves estrangeiras para evitar erros
DROP TABLE IF EXISTS attendances;
DROP TABLE IF EXISTS medical_records;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS medications;
DROP TABLE IF EXISTS professionals;

-- 1. Profissionais
CREATE TABLE professionals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- 2. Pacientes
CREATE TABLE patients (
    cpf VARCHAR(11) PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    birth_date DATE,
    gender CHAR(1)
);

-- 3. Prontuários
CREATE TABLE medical_records (
    patient_cpf VARCHAR(11) PRIMARY KEY,
    opening_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observations TEXT,
    CONSTRAINT fk_record_patient FOREIGN KEY (patient_cpf) REFERENCES patients(cpf) ON DELETE RESTRICT
);

-- 4. Medicamentos
CREATE TABLE medications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    concentration VARCHAR(50)
);

-- 5. Atendimentos
CREATE TABLE attendances (
    patient_cpf VARCHAR(11) NOT NULL,
    attendance_datetime DATETIME NOT NULL,
    professional_id INT NOT NULL,
    glasgow_scale INT NOT NULL,
    triage_classification VARCHAR(20) NOT NULL,
    blood_pressure VARCHAR(10),
    heart_rate INT,
    oxygen_saturation INT,
    administered_medications JSON,
    synchronized BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (patient_cpf, attendance_datetime),
    CONSTRAINT fk_attendance_record FOREIGN KEY (patient_cpf) REFERENCES medical_records(patient_cpf) ON DELETE RESTRICT,
    CONSTRAINT fk_attendance_professional FOREIGN KEY (professional_id) REFERENCES professionals(id) ON DELETE RESTRICT
);