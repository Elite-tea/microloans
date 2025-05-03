-- Удаление таблиц от наиболее зависимых к независимым
DROP TABLE IF EXISTS contracts CASCADE;
DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS clients CASCADE;
DROP TABLE IF EXISTS power_of_attorneys CASCADE;
DROP TABLE IF EXISTS issue_points CASCADE;
DROP TABLE IF EXISTS statuses CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- Таблица ролей
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT uk_role_name UNIQUE (name)
);

-- Таблица статусов
CREATE TABLE IF NOT EXISTS statuses (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT uk_status_name UNIQUE (name)
);

-- Таблица точек выдачи
CREATE TABLE IF NOT EXISTS issue_points (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    CONSTRAINT uk_issue_point_name UNIQUE (name)
);

-- Таблица доверенностей
CREATE TABLE IF NOT EXISTS power_of_attorneys (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    number VARCHAR(50) NOT NULL,
    date_poa TIMESTAMP NOT NULL,
    CONSTRAINT uk_poa_number UNIQUE (number)
);

-- Таблица клиентов
CREATE TABLE IF NOT EXISTS clients (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    CONSTRAINT uk_client_phone UNIQUE (phone)
);

-- Таблица сотрудников
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    login VARCHAR(20) NOT NULL,
    password VARCHAR(350) NOT NULL,
    role_id BIGINT NOT NULL,
    issue_point_id BIGINT NOT NULL,
    power_of_attorney_id BIGINT NOT NULL,
    FOREIGN KEY (power_of_attorney_id) REFERENCES power_of_attorneys(id),
    FOREIGN KEY (issue_point_id) REFERENCES issue_points(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT uk_employee_login UNIQUE (login)
);

-- Таблица договоров
CREATE TABLE IF NOT EXISTS contracts (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    client_id BIGINT NOT NULL,
    amount DECIMAL(20,2) NOT NULL,
    issue_date TIMESTAMP NOT NULL,
    employee_id BIGINT NOT NULL,
    term_date TIMESTAMP NOT NULL,
    issue_point_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (issue_point_id) REFERENCES issue_points(id),
    FOREIGN KEY (status_id) REFERENCES statuses(id)
);

-- Создание индексов для улучшения производительности
CREATE INDEX idx_contracts_client_id ON contracts(client_id);
CREATE INDEX idx_contracts_status_id ON contracts(status_id);
CREATE INDEX idx_contracts_issue_date ON contracts(issue_date);
CREATE INDEX idx_employees_role_id ON employees(role_id);
CREATE INDEX idx_employees_issue_point_id ON employees(issue_point_id);