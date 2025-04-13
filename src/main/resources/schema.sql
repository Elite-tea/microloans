-- Удаление существующих таблиц (в правильном порядке для соблюдения внешних ключей)
DROP TABLE IF EXISTS contract CASCADE;
DROP TABLE IF EXISTS power_of_attorney CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS issue_point CASCADE;

-- Создание таблицы точек выдачи
CREATE TABLE IF NOT EXISTS issue_point (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Создание таблицы сотрудников
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    issue_point_id BIGINT NOT NULL,
    login VARCHAR(20) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (issue_point_id) REFERENCES issue_point(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Создание таблицы доверенностей
CREATE TABLE IF NOT EXISTS power_of_attorney (
    id BIGINT PRIMARY KEY,
    number VARCHAR(50) NOT NULL,
    date_poa DATE NOT NULL,
);

-- Создание таблицы клиентов
CREATE TABLE IF NOT EXISTS client (
    id BIGINT PRIMARY KEY,
    full_name_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
);

-- Создание таблицы договоров
CREATE TABLE IF NOT EXISTS contract (
    id BIGINT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    issue_date DATE NOT NULL,
    employee_id BIGINT NOT NULL,
    term_date DATE NOT NULL,
    issue_point_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (employee_id) REFERENCES employee(id),
    FOREIGN KEY (issue_point_id) REFERENCES issue_point(id),
    FOREIGN KEY (status_id) REFERENCES statuses(id)
);

-- Создание таблицы статусов
CREATE TABLE IF NOT EXISTS role (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Создание таблицы ролей
CREATE TABLE IF NOT EXISTS statuses (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);