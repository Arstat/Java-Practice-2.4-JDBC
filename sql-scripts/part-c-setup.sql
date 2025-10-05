DROP DATABASE IF EXISTS school_db;
CREATE DATABASE school_db;
USE school_db;

CREATE TABLE Student (
    StudentID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Department VARCHAR(50) NOT NULL,
    Marks DECIMAL(5, 2) NOT NULL CHECK (Marks >= 0 AND Marks <= 100)
);

INSERT INTO Student (Name, Department, Marks) VALUES
('Alice Johnson', 'Computer Science', 92.5),
('Bob Smith', 'Electrical Engineering', 78.0),
('Charlie Brown', 'Computer Science', 85.5),
('Diana Prince', 'Mechanical Engineering', 88.0),
('Eve Adams', 'Computer Science', 95.0),
('Frank Miller', 'Civil Engineering', 72.0),
('Grace Lee', 'Computer Science', 91.0),
('Henry Wilson', 'Electrical Engineering', 83.5),
('Iris Chen', 'Mechanical Engineering', 87.0),
('Jack Davis', 'Civil Engineering', 76.5);

SELECT 'Student table created!' as Status;
SELECT COUNT(*) as 'Total Students' FROM Student;
