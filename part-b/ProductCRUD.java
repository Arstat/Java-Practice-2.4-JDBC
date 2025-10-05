import java.sql.*;
import java.util.Scanner;

/**
 * Part B: Menu-Driven CRUD Operations on Product Table
 * Includes transaction handling for data integrity
 */
public class ProductCRUD {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    createProduct(scanner);
                    break;
                case 2:
                    readProducts();
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            System.out.println();
        } while (choice != 5);
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n========== PRODUCT MANAGEMENT SYSTEM ==========");
        System.out.println("1. Create - Add New Product");
        System.out.println("2. Read - View All Products");
        System.out.println("3. Update - Modify Product Details");
        System.out.println("4. Delete - Remove Product");
        System.out.println("5. Exit");
        System.out.println("===============================================");
    }
    
    // CREATE Operation
    private static void createProduct(Scanner scanner) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false); // Start transaction
            
            System.out.println("\n--- Add New Product ---");
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();
            
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            
            String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            
            int rows = pstmt.executeUpdate();
            conn.commit(); // Commit transaction
            
            System.out.println("Product added successfully! Rows affected: " + rows);
            
        } catch (SQLException e) {
            System.err.println("Error creating product!");
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                    System.out.println("Transaction rolled back.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
    }
    
    // READ Operation
    private static void readProducts() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
            
            String sql = "SELECT * FROM Product";
            rs = stmt.executeQuery(sql);
            
            System.out.println("\n==================== PRODUCT LIST ====================");
            System.out.printf("%-12s %-25s %-12s %-10s%n", 
                "ProductID", "ProductName", "Price", "Quantity");
            System.out.println("======================================================");
            
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                
                System.out.printf("%-12d %-25s $%-11.2f %-10d%n", 
                    id, name, price, quantity);
            }
            
            if (!hasData) {
                System.out.println("No products found in database.");
            }
            System.out.println("======================================================");
            
        } catch (SQLException e) {
            System.err.println("Error reading products!");
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    // UPDATE Operation
    private static void updateProduct(Scanner scanner) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false); // Start transaction
            
            System.out.println("\n--- Update Product ---");
            System.out.print("Enter Product ID to update: ");
            int productId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter New Product Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter New Price: ");
            double price = scanner.nextDouble();
            
            System.out.print("Enter New Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            
            String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, productId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                conn.commit(); // Commit transaction
                System.out.println("Product updated successfully! Rows affected: " + rows);
            } else {
                conn.rollback();
                System.out.println("No product found with ID: " + productId);
            }
            
        } catch (SQLException e) {
            System.err.println("Error updating product!");
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
    }
    
    // DELETE Operation
    private static void deleteProduct(Scanner scanner) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false); // Start transaction
            
            System.out.println("\n--- Delete Product ---");
            System.out.print("Enter Product ID to delete: ");
            int productId = scanner.nextInt();
            scanner.nextLine();
            
            String sql = "DELETE FROM Product WHERE ProductID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                conn.commit(); // Commit transaction
                System.out.println("Product deleted successfully! Rows affected: " + rows);
            } else {
                conn.rollback();
                System.out.println("No product found with ID: " + productId);
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting product!");
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }
    }
    
    // Utility method to close resources
    private static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/*
 * SQL Script to Create Product Table:
 * 
 * CREATE DATABASE IF NOT EXISTS inventory_db;
 * USE inventory_db;
 * 
 * CREATE TABLE Product (
 *     ProductID INT PRIMARY KEY AUTO_INCREMENT,
 *     ProductName VARCHAR(100) NOT NULL,
 *     Price DECIMAL(10, 2) NOT NULL,
 *     Quantity INT NOT NULL
 * );
 */