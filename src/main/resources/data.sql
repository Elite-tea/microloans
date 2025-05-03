-- Роли
INSERT INTO roles (id, name) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- Статусы
INSERT INTO statuses (id, name) VALUES
(1, 'ACTIVE'),
(2, 'CLOSED');

-- Точки выдачи
INSERT INTO issue_points (name, address) VALUES
('Центральный офис', 'ул. Ленина, 1'),
('Северный филиал', 'ул. Северная, 10'),
('Южный филиал', 'ул. Южная, 15');

-- Доверенности
INSERT INTO power_of_attorneys (number, date_poa) VALUES
('DOV-001', '2024-01-01T13:00:00'),
('DOV-002', '2024-01-02T13:00:00'),
('DOV-003', '2024-01-03T13:00:00'),
('DOV-004', '2024-01-04T13:00:00'),
('DOV-005', '2024-01-05T13:00:00');


-- Сотрудники
INSERT INTO employees
 (full_name, login, password, role_id, issue_point_id, power_of_attorney_id) VALUES
-- Админы
('Иванов Иван', 'admin1', '$2a$10$zYX3r.1D6jhdEu4PV3Dk5.HEw0TkgTKEaZNQZGP.R6plPkzOONj96', 1, 2, 2), -- pass: adm1
('Петров Петр', 'admin2', '$2a$10$zYX3r.1D6jhdEu4PV3Dk5.HEw0TkgTKEaZNQZGP.R6plPkzOONj96', 1, 2, 2), -- pass: adm2
-- Пользователи
('Сидоров Сидор', 'user1', '$2a$10$zYX3r.1D6jhdEu4PV3Dk5.HEw0TkgTKEaZNQZGP.R6plPkzOONj96', 2, 1, 3), -- pass: usr1
('Николаев Николай', 'user2', '$2a$10$zYX3r.1D6jhdEu4PV3Dk5.HEw0TkgTKEaZNQZGP.R6plPkzOONj96', 2, 2, 4), -- pass: usr2
('Андреев Андрей', 'user3', '$2a$10$zYX3r.1D6jhdEu4PV3Dk5.HEw0TkgTKEaZNQZGP.R6plPkzOONj96', 2, 3, 5); -- pass: usr3

-- Клиенты
INSERT INTO clients (full_name, phone) VALUES
('Смирнов Алексей', '+79001234567'),
('Козлова Мария', '+79009876543'),
('Васильев Дмитрий', '+79007894561'),
('Морозова Елена', '+79003216547'),
('Попов Артем', '+79005432198');

-- Договоры
INSERT INTO contracts (client_id, amount, issue_date, term_date, employee_id, issue_point_id, status_id) VALUES
(1, 50000.00, '2024-01-10T13:00:00', '2024-07-10T13:00:00', 1, 1, 1),
(2, 75000.00, '2024-01-15T13:00:00', '2024-07-15T13:00:00', 2, 2, 1),
(3, 100000.00, '2024-01-20T13:00:00', '2024-07-20T13:00:00', 3, 1, 1),
(4, 150000.00, '2024-01-25T13:00:00', '2024-07-25T13:00:00', 4, 2, 2),
(5, 200000.00, '2024-01-30T13:00:00', '2024-07-30T13:00:00', 5, 3, 2);