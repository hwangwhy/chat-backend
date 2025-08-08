-- Tạo database cho chat application
CREATE DATABASE IF NOT EXISTS chatdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Sử dụng database vừa tạo
USE chatdb;

-- Tạo user cho ứng dụng (optional - để bảo mật tốt hơn)
-- CREATE USER IF NOT EXISTS 'chatapp_user'@'localhost' IDENTIFIED BY 'chatapp_password';
-- GRANT ALL PRIVILEGES ON chatdb.* TO 'chatapp_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Tables sẽ được tự động tạo bởi JPA/Hibernate khi start ứng dụng

-- Nếu bạn muốn thêm cột mới vào table messages đã tồn tại, chạy lệnh này:
-- ALTER TABLE messages ADD COLUMN message_type VARCHAR(20) DEFAULT 'text';
-- ALTER TABLE messages ADD COLUMN image_url VARCHAR(500); 