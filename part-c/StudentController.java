import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Part C: Student Management System - MVC Architecture
 * CONTROLLER: StudentController Class
 * 
 * Handles all database operations for Student management
 */
public class StudentController {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/school_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    // Establish database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    /**
     * CREATE: Add a new student to the database
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setDouble(3, student.getMarks());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Retrieve the auto-generated StudentID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    student.setStudentId(generatedKeys.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * READ: Retrieve all students from the database
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student ORDER BY StudentID";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Marks")
                );
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }
    
    /**
     * READ: Retrieve a specific student by ID
     */
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM Student WHERE StudentID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Student(
                    rs.getInt("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Marks")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * UPDATE: Modify student details
     */
    public boolean updateStudent(Student student) {
        String sql = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false); // Start transaction
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setDouble(3, student.getMarks());
            pstmt.setInt(4, student.getStudentId());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * DELETE: Remove a student from the database
     */
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM Student WHERE StudentID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false); // Start transaction
            
            pstmt.setInt(1, studentId);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Additional utility method: Get students by department
     */
    public List<Student> getStudentsByDepartment(String department) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student WHERE Department = ? ORDER BY Marks DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Marks")
                );
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving students by department: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }
    
    /**
     * Get statistics: Average marks
     */
    public double getAverageMarks() {
        String sql = "SELECT AVG(Marks) as average FROM Student";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("average");
            }
            
        } catch (SQLException e) {
            System.err.println("Error calculating average marks: " + e.getMessage());
            e.printStackTrace();
        }
        return 0.0;
    }
}