DROP DATABASE IF EXISTS company_db;
CREATE DATABASE company_db;
USE company_db;

CREATE TABLE Employee (
    EmpID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Salary DECIMAL(10, 2) NOT NULL
);

INSERT INTO Employee (Name, Salary) VALUES
('John Doe', 55000.00),
('Jane Smith', 62000.00),
('Robert Johnson', 58000.00),
('Emily Davis', 65000.00),
('Michael Wilson', 60000.00),
('Sarah Anderson', 58500.00),
('David Martinez', 67000.00),
('Lisa Taylor', 61500.00);

SELECT 'Employee table created!' as Status;
SELECT COUNT(*) as 'Total Employees' FROM Employee;
