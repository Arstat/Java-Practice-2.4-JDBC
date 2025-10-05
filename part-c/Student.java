/**
 * Part C: Student Management System - MVC Architecture
 * MODEL: Student Class
 * 
 * Represents the data structure for a student
 */
public class Student {
    private int studentId;
    private String name;
    private String department;
    private double marks;
    
    // Default Constructor
    public Student() {
    }
    
    // Parameterized Constructor
    public Student(int studentId, String name, String department, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
    
    // Constructor without ID (for new student creation)
    public Student(String name, String department, double marks) {
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
    
    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public double getMarks() {
        return marks;
    }
    
    public void setMarks(double marks) {
        this.marks = marks;
    }
    
    // toString method for easy display
    @Override
    public String toString() {
        return String.format("Student[ID=%d, Name=%s, Department=%s, Marks=%.2f]",
                studentId, name, department, marks);
    }
    
    // Method to get grade based on marks
    public String getGrade() {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }
}