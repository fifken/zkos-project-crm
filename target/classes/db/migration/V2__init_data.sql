DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM roles;

INSERT INTO nasabah (id, cabang, nama, no_kontrak, sisahutang, status, totalhutang)
VALUES 
(1, 'Jakarta', 'Budi Santoso', 'CNTR12345', 1500000, 'Belum Lunas', 5000000),
(2, 'Bandung', 'Siti Aminah', 'CNTR12346', 2000000, 'Belum Lunas', 6000000),
(3, 'Surabaya', 'Ahmad Yusuf', 'CNTR12347', 0, 'Lunas', 3000000);

INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO users (id, username, password) VALUES (1, 'admin', '$2a$10$oRm5ddTjHMhNsi.NVKRLQOiUXz6qs1zPUhkZGd5TM/7Y0F/MLkBae');
INSERT INTO users (id, username, password) VALUES (2, 'user', '$2a$10$4koVDNa1I8P6u2LqN4fx5e1hSgdkabthueqrWpTJGi9GFIIDUD4AC');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1);