#!/bin/bash
echo "Setting up JDBC environment..."

# Install MySQL
sudo apt-get update -qq
sudo DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server

# Start MySQL
sudo service mysql start
sleep 3

# Configure MySQL
sudo mysql << 'EOSQL'
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
CREATE USER IF NOT EXISTS 'javauser'@'localhost' IDENTIFIED BY 'java123';
GRANT ALL PRIVILEGES ON *.* TO 'javauser'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
EOSQL

# Download JDBC driver
mkdir -p lib
cd lib
wget -q https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar
cd ..

# Create databases
mysql -u javauser -pjava123 << 'EOSQL'
CREATE DATABASE IF NOT EXISTS company_db;
CREATE DATABASE IF NOT EXISTS inventory_db;
CREATE DATABASE IF NOT EXISTS school_db;

USE company_db;
CREATE TABLE IF NOT EXISTS Employee (
    EmpID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Salary DECIMAL(10, 2) NOT NULL
);
INSERT INTO Employee (Name, Salary) VALUES
('John Doe', 55000.00),
('Jane Smith', 62000.00),
('Robert Johnson', 58000.00),
('Emily Davis', 65000.00);

USE inventory_db;
CREATE TABLE IF NOT EXISTS Product (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Quantity INT NOT NULL
);
INSERT INTO Product (ProductName, Price, Quantity) VALUES
('Laptop', 899.99, 15),
('Wireless Mouse', 25.50, 50);

USE school_db;
CREATE TABLE IF NOT EXISTS Student (
    StudentID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Department VARCHAR(50) NOT NULL,
    Marks DECIMAL(5, 2) NOT NULL
);
INSERT INTO Student (Name, Department, Marks) VALUES
('Alice Johnson', 'Computer Science', 92.5),
('Bob Smith', 'Electrical Engineering', 78.0);
EOSQL

echo "Setup complete!"
