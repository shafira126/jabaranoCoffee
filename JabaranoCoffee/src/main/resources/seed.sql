-- Data awal: 1 akun staff + data meja Jabarano Coffee

USE jabarano_coffee;

-- Data staff
-- Password: admin123
INSERT INTO users (username, password) VALUES
('admin', 'admin123');

-- Data meja
INSERT INTO cafe_tables (table_number, capacity, status) VALUES
('T01', 2,  'AVAILABLE'),
('T02', 2,  'AVAILABLE'),
('T03', 4,  'AVAILABLE'),
('T04', 4,  'AVAILABLE'),
('T05', 4,  'AVAILABLE'),
('T06', 6,  'AVAILABLE'),
('T07', 6,  'AVAILABLE'),
('T08', 8,  'AVAILABLE'),
('T09', 8,  'AVAILABLE'),
('T10', 10, 'AVAILABLE');