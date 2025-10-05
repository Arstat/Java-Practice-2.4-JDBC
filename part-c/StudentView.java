import java.util.List;
import java.util.Scanner;

/**
 * Part C: Student Management System - MVC Architecture
 * VIEW: StudentView Class (Main Application)
 * 
 * Provides the user interface and handles user interactions
 * 
 * IMPORTANT: Make sure Student.java and StudentController.java 
 * are in the same directory and compiled first!
 */
public class StudentView {
    
    private StudentController controller;
    private Scanner scanner;
    
    public StudentView() {
        this.controller = new StudentController();
        this.scanner = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        StudentView view = new StudentView();
        view.run();
    }
    
    public void run() {
        int choice;
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   STUDENT MANAGEMENT SYSTEM - MVC DEMO     ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        addNewStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        viewStudentById();
                        break;
                    case 4:
                        updateStudentDetails();
                        break;
                    case 5:
                        deleteStudentRecord();
                        break;
                    case 6:
                        viewByDepartment();
                        break;
                    case 7:
                        viewStatistics();
                        break;
                    case 8:
                        System.out.println("\n✓ Thank you for using Student Management System!");
                        System.out.println("✓ Exiting application. Goodbye!\n");
                        break;
                    default:
                        System.out.println("\n✗ Invalid choice! Please enter a number between 1 and 8.\n");
                }
            } catch (Exception e) {
                System.out.println("\n✗ Invalid input! Please enter a valid number.\n");
                scanner.nextLine(); // Clear invalid input
                choice = 0;
            }
            
        } while (choice != 8);
        
        scanner.close();
    }
    
    private void displayMenu() {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│           MAIN MENU                        │");
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│  1. Add New Student                        │");
        System.out.println("│  2. View All Students                      │");
        System.out.println("│  3. Search Student by ID                   │");
        System.out.println("│  4. Update Student Details                 │");
        System.out.println("│  5. Delete Student Record                  │");
        System.out.println("│  6. View Students by Department            │");
        System.out.println("│  7. View Statistics                        │");
        System.out.println("│  8. Exit                                   │");
        System.out.println("└────────────────────────────────────────────┘");
    }
    
    // Option 1: Add New Student
    private void addNewStudent() {
        System.out.println("\n┌─── ADD NEW STUDENT ───────────────────────┐");
        
        try {
            System.out.print("│ Enter Student Name: ");
            String name = scanner.nextLine();
            
            System.out.print("│ Enter Department: ");
            String department = scanner.nextLine();
            
            System.out.print("│ Enter Marks (0-100): ");
            double marks = scanner.nextDouble();
            scanner.nextLine();
            
            if (marks < 0 || marks > 100) {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✗ Invalid marks! Must be between 0 and 100.\n");
                return;
            }
            
            Student student = new Student(name, department, marks);
            
            if (controller.addStudent(student)) {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✓ Student added successfully!");
                System.out.println("  Student ID: " + student.getStudentId());
                System.out.println("  Grade: " + student.getGrade() + "\n");
            } else {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✗ Failed to add student.\n");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Error: Invalid input format!\n");
            scanner.nextLine();
        }
    }
    
    // Option 2: View All Students
    private void viewAllStudents() {
        System.out.println("\n┌─── ALL STUDENTS ──────────────────────────────────────────────────────┐");
        
        List<Student> students = controller.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("│ No students found in the database.                                   │");
            System.out.println("└───────────────────────────────────────────────────────────────────────┘\n");
            return;
        }
        
        System.out.println("├───────────┬─────────────────────────┬──────────────────┬────────┬───────┤");
        System.out.printf("│ %-9s │ %-23s │ %-16s │ %-6s │ %-5s │%n",
                "StudentID", "Name", "Department", "Marks", "Grade");
        System.out.println("├───────────┼─────────────────────────┼──────────────────┼────────┼───────┤");
        
        for (Student student : students) {
            System.out.printf("│ %-9d │ %-23s │ %-16s │ %6.2f │ %-5s │%n",
                    student.getStudentId(),
                    student.getName(),
                    student.getDepartment(),
                    student.getMarks(),
                    student.getGrade());
        }
        
        System.out.println("└───────────┴─────────────────────────┴──────────────────┴────────┴───────┘");
        System.out.println("Total Students: " + students.size() + "\n");
    }
    
    // Option 3: Search Student by ID
    private void viewStudentById() {
        System.out.println("\n┌─── SEARCH STUDENT ────────────────────────┐");
        
        try {
            System.out.print("│ Enter Student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            
            Student student = controller.getStudentById(studentId);
            
            System.out.println("└───────────────────────────────────────────┘");
            
            if (student != null) {
                System.out.println("\n✓ Student Found:");
                System.out.println("  ID: " + student.getStudentId());
                System.out.println("  Name: " + student.getName());
                System.out.println("  Department: " + student.getDepartment());
                System.out.println("  Marks: " + student.getMarks());
                System.out.println("  Grade: " + student.getGrade() + "\n");
            } else {
                System.out.println("✗ No student found with ID: " + studentId + "\n");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Error: Invalid input format!\n");
            scanner.nextLine();
        }
    }
    
    // Option 4: Update Student Details
    private void updateStudentDetails() {
        System.out.println("\n┌─── UPDATE STUDENT ────────────────────────┐");
        
        try {
            System.out.print("│ Enter Student ID to update: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            
            Student existingStudent = controller.getStudentById(studentId);
            
            if (existingStudent == null) {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✗ No student found with ID: " + studentId + "\n");
                return;
            }
            
            System.out.println("│");
            System.out.println("│ Current Details:");
            System.out.println("│   Name: " + existingStudent.getName());
            System.out.println("│   Department: " + existingStudent.getDepartment());
            System.out.println("│   Marks: " + existingStudent.getMarks());
            System.out.println("│");
            System.out.println("│ Enter New Details:");
            
            System.out.print("│ New Name: ");
            String name = scanner.nextLine();
            
            System.out.print("│ New Department: ");
            String department = scanner.nextLine();
            
            System.out.print("│ New Marks (0-100): ");
            double marks = scanner.nextDouble();
            scanner.nextLine();
            
            if (marks < 0 || marks > 100) {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✗ Invalid marks! Must be between 0 and 100.\n");
                return;
            }
            
            Student updatedStudent = new Student(studentId, name, department, marks);
            
            if (controller.updateStudent(updatedStudent)) {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✓ Student details updated successfully!");
                System.out.println("  New Grade: " + updatedStudent.getGrade() + "\n");
            } else {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✗ Failed to update student.\n");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Error: Invalid input format!\n");
            scanner.nextLine();
        }
    }
    
    // Option 5: Delete Student Record
    private void deleteStudentRecord() {
        System.out.println("\n┌─── DELETE STUDENT ────────────────────────┐");
        
        try {
            System.out.print("│ Enter Student ID to delete: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            
            Student student = controller.getStudentById(studentId);
            
            if (student == null) {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✗ No student found with ID: " + studentId + "\n");
                return;
            }
            
            System.out.println("│");
            System.out.println("│ Student to be deleted:");
            System.out.println("│   Name: " + student.getName());
            System.out.println("│   Department: " + student.getDepartment());
            System.out.println("│");
            System.out.print("│ Are you sure? (yes/no): ");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
                if (controller.deleteStudent(studentId)) {
                    System.out.println("└───────────────────────────────────────────┘");
                    System.out.println("✓ Student record deleted successfully!\n");
                } else {
                    System.out.println("└───────────────────────────────────────────┘");
                    System.out.println("✗ Failed to delete student.\n");
                }
            } else {
                System.out.println("└───────────────────────────────────────────┘");
                System.out.println("✓ Deletion cancelled.\n");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Error: Invalid input format!\n");
            scanner.nextLine();
        }
    }
    
    // Option 6: View Students by Department
    private void viewByDepartment() {
        System.out.println("\n┌─── FILTER BY DEPARTMENT ──────────────────┐");
        System.out.print("│ Enter Department Name: ");
        String department = scanner.nextLine();
        
        List<Student> students = controller.getStudentsByDepartment(department);
        
        System.out.println("└───────────────────────────────────────────┘");
        
        if (students.isEmpty()) {
            System.out.println("✗ No students found in department: " + department + "\n");
            return;
        }
        
        System.out.println("\n┌─── STUDENTS IN " + department.toUpperCase() + " ────────────────────────────────────────┐");
        System.out.println("├───────────┬─────────────────────────┬────────┬───────┤");
        System.out.printf("│ %-9s │ %-23s │ %-6s │ %-5s │%n",
                "StudentID", "Name", "Marks", "Grade");
        System.out.println("├───────────┼─────────────────────────┼────────┼───────┤");
        
        for (Student student : students) {
            System.out.printf("│ %-9d │ %-23s │ %6.2f │ %-5s │%n",
                    student.getStudentId(),
                    student.getName(),
                    student.getMarks(),
                    student.getGrade());
        }
        
        System.out.println("└───────────┴─────────────────────────┴────────┴───────┘");
        System.out.println("Total Students: " + students.size() + "\n");
    }
    
    // Option 7: View Statistics
    private void viewStatistics() {
        System.out.println("\n┌─── STATISTICS ────────────────────────────┐");
        
        List<Student> students = controller.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("│ No data available for statistics.        │");
            System.out.println("└───────────────────────────────────────────┘\n");
            return;
        }
        
        double average = controller.getAverageMarks();
        int totalStudents = students.size();
        
        // Calculate grade distribution
        int[] gradeCount = new int[6]; // A+, A, B, C, D, F
        
        for (Student student : students) {
            String grade = student.getGrade();
            switch (grade) {
                case "A+": gradeCount[0]++; break;
                case "A": gradeCount[1]++; break;
                case "B": gradeCount[2]++; break;
                case "C": gradeCount[3]++; break;
                case "D": gradeCount[4]++; break;
                case "F": gradeCount[5]++; break;
            }
        }
        
        System.out.println("│                                           │");
        System.out.printf("│ Total Students: %-25d │%n", totalStudents);
        System.out.printf("│ Average Marks: %-26.2f │%n", average);
        System.out.println("│                                           │");
        System.out.println("│ Grade Distribution:                       │");
        System.out.printf("│   A+ : %-3d  (%.1f%%)                      │%n", 
                gradeCount[0], (gradeCount[0] * 100.0 / totalStudents));
        System.out.printf("│   A  : %-3d  (%.1f%%)                      │%n", 
                gradeCount[1], (gradeCount[1] * 100.0 / totalStudents));
        System.out.printf("│   B  : %-3d  (%.1f%%)                      │%n", 
                gradeCount[2], (gradeCount[2] * 100.0 / totalStudents));
        System.out.printf("│   C  : %-3d  (%.1f%%)                      │%n", 
                gradeCount[3], (gradeCount[3] * 100.0 / totalStudents));
        System.out.printf("│   D  : %-3d  (%.1f%%)                      │%n", 
                gradeCount[4], (gradeCount[4] * 100.0 / totalStudents));
        System.out.printf("│   F  : %-3d  (%.1f%%)                      │%n", 
                gradeCount[5], (gradeCount[5] * 100.0 / totalStudents));
        System.out.println("└───────────────────────────────────────────┘\n");
    }
}

/*
 * SQL Script to Create Student Table:
 * 
 * CREATE DATABASE IF NOT EXISTS school_db;
 * USE school_db;
 * 
 * CREATE TABLE Student (
 *     StudentID INT PRIMARY KEY AUTO_INCREMENT,
 *     Name VARCHAR(100) NOT NULL,
 *     Department VARCHAR(50) NOT NULL,
 *     Marks DECIMAL(5, 2) NOT NULL CHECK (Marks >= 0 AND Marks <= 100)
 * );
 * 
 * Sample Data:
 * INSERT INTO Student (Name, Department, Marks) VALUES
 * ('Alice Johnson', 'Computer Science', 92.5),
 * ('Bob Smith', 'Electrical Engineering', 78.0),
 * ('Charlie Brown', 'Computer Science', 85.5),
 * ('Diana Prince', 'Mechanical Engineering', 88.0),
 * ('Eve Adams', 'Computer Science', 95.0);
 */
