import java.sql.*;

/**
 * Part A: Connecting to MySQL and Fetching Data from Employee Table
 * This program demonstrates basic JDBC connectivity and SELECT operation
 */
public class EmployeeDataFetch {
    
    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/company_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // Step 1: Load JDBC Driver (optional for JDBC 4.0+)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");
            
            // Step 2: Establish Connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database successfully!\n");
            
            // Step 3: Create Statement
            stmt = conn.createStatement();
            
            // Step 4: Execute Query
            String sql = "SELECT EmpID, Name, Salary FROM Employee";
            rs = stmt.executeQuery(sql);
            
            // Step 5: Display Results
            System.out.println("=================================================");
            System.out.printf("%-10s %-25s %-15s%n", "EmpID", "Name", "Salary");
            System.out.println("=================================================");
            
            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                
                System.out.printf("%-10d %-25s $%-14.2f%n", empId, name, salary);
            }
            
            System.out.println("=================================================");
            System.out.println("\nData fetched successfully!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error occurred!");
            e.printStackTrace();
        } finally {
            // Step 6: Close Resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 * SQL Script to Create Employee Table:
 * 
 * CREATE DATABASE IF NOT EXISTS company_db;
 * USE company_db;
 * 
 * CREATE TABLE Employee (
 *     EmpID INT PRIMARY KEY AUTO_INCREMENT,
 *     Name VARCHAR(100) NOT NULL,
 *     Salary DECIMAL(10, 2) NOT NULL
 * );
 * 
 * INSERT INTO Employee (Name, Salary) VALUES
 * ('John Doe', 55000.00),
 * ('Jane Smith', 62000.00),
 * ('Robert Johnson', 58000.00),
 * ('Emily Davis', 65000.00);
 */