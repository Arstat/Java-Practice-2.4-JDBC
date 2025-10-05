DROP DATABASE IF EXISTS inventory_db;
CREATE DATABASE inventory_db;
USE inventory_db;

CREATE TABLE Product (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Quantity INT NOT NULL
);

INSERT INTO Product (ProductName, Price, Quantity) VALUES
('Laptop', 899.99, 15),
('Wireless Mouse', 25.50, 50),
('Mechanical Keyboard', 89.99, 30),
('4K Monitor', 399.99, 20),
('USB-C Hub', 45.00, 40),
('Webcam HD', 75.50, 25);

SELECT 'Product table created!' as Status;
SELECT COUNT(*) as 'Total Products' FROM Product;
