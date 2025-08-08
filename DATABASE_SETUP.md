# Hướng dẫn Setup MySQL Database

## 1. Cài đặt MySQL

### Windows:
1. Download MySQL Community Server từ: https://dev.mysql.com/downloads/mysql/
2. Chạy installer và follow hướng dẫn
3. Nhớ password của root user

### macOS:
```bash
brew install mysql
brew services start mysql
```

### Ubuntu/Linux:
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

## 2. Tạo Database

### Cách 1: Sử dụng MySQL Command Line
```bash
mysql -u root -p
```

Sau đó chạy:
```sql
CREATE DATABASE chatdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit;
```

### Cách 2: Chạy script có sẵn
```bash
mysql -u root -p < src/main/resources/create_database.sql
```

## 3. Cập nhật password trong config

Chỉnh sửa file `application.properties`:
```properties
spring.datasource.password=your_mysql_password
```

## 4. Chạy ứng dụng

```bash
mvn spring-boot:run
```

Hoặc với profile development:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 5. Kiểm tra Database

Tables sẽ được tự động tạo:
- `users` - Lưu thông tin user
- `messages` - Lưu tin nhắn chat

### Kiểm tra bằng MySQL CLI:
```bash
mysql -u root -p
use chatdb;
show tables;
describe users;
describe messages;
```

## 6. Troubleshooting

### Lỗi kết nối:
- Kiểm tra MySQL service đã chạy
- Kiểm tra port 3306
- Kiểm tra username/password

### Lỗi timezone:
Database URL đã include `serverTimezone=UTC`

### Lỗi SSL:
Database URL đã include `useSSL=false` cho development 