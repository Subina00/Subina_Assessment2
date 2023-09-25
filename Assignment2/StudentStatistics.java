import java.io.*;
import java.util.*;

public class StudentStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Read students' marks from a file");
            System.out.println("2. Calculate and display total marks");
            System.out.println("3. List students with total marks below a threshold");
            System.out.println("4. List top 5 students with highest and lowest total marks");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the file name: ");
                    String fileName = scanner.nextLine();
                    students = readStudentsFromFile(fileName);
                    break;
                case 2:
                    if (students.isEmpty()) {
                        System.out.println("No student data available. Please read data from a file first.");
                    } else {
                        calculateTotalMarks(students);
                        displayStudentData(students);
                    }
                    break;
                case 3:
                    if (students.isEmpty()) {
                        System.out.println("No student data available. Please read data from a file first.");
                    } else {
                        System.out.print("Enter the threshold for total marks: ");
                        double threshold = scanner.nextDouble();
                        listStudentsBelowThreshold(students, threshold);
                    }
                    break;
                case 4:
                    if (students.isEmpty()) {
                        System.out.println("No student data available. Please read data from a file first.");
                    } else {
                        listTopAndBottomStudents(students);
                    }
                    break;
                case 5:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static List<Student> readStudentsFromFile(String fileName) {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Last Name,First Name,Student ID,A1,A2,A3")) {
                    continue; // Skip the header line
                }

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String lastName = parts[0];
                    String firstName = parts[1];
                    int studentID = Integer.parseInt(parts[2]);
                    double a1 = Double.parseDouble(parts[3]);
                    double a2 = Double.parseDouble(parts[4]);
                    double a3 = parts.length > 5 ? Double.parseDouble(parts[5]) : 0.0;

                    Student student = new Student(lastName, firstName, studentID, a1, a2, a3);
                    students.add(student);
                }
            }
            System.out.println("Data read successfully from the file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        return students;
    }

    private static void calculateTotalMarks(List<Student> students) {
        for (Student student : students) {
            student.calculateTotalMarks();
        }
    }

    private static void displayStudentData(List<Student> students) {
        System.out.printf("%-20s %-20s %-12s %-12s %-12s %-12s%n",
                "Last Name", "First Name", "Student ID", "Assignment 1", "Assignment 2", "Assignment 3");
        for (Student student : students) {
            System.out.printf("%-20s %-20s %-12d %-12.1f %-12.1f %-12.1f%n",
                    student.getLastName(), student.getFirstName(), student.getStudentID(),
                    student.getA1(), student.getA2(), student.getA3());
        }
    }

    private static void listStudentsBelowThreshold(List<Student> students, double threshold) {
        System.out.printf("Students with total marks below %.2f:%n", threshold);
        System.out.printf("%-20s %-20s %-12s %-12s%n", "Last Name", "First Name", "Student ID", "Total Marks");
        for (Student student : students) {
            if (student.getTotalMarks() < threshold) {
                System.out.printf("%-20s %-20s %-12d %-12.2f%n",
                        student.getLastName(), student.getFirstName(),
                        student.getStudentID(), student.getTotalMarks());
            }
        }
    }

    private static void listTopAndBottomStudents(List<Student> students) {
        students.sort(Comparator.comparingDouble(Student::getTotalMarks));

        System.out.println("Top 5 students with the highest total marks:");
        System.out.printf("%-20s %-20s %-12s %-12s%n", "Last Name", "First Name", "Student ID", "Total Marks");
        for (int i = students.size() - 1; i >= Math.max(students.size() - 5, 0); i--) {
            Student student = students.get(i);
            System.out.printf("%-20s %-20s %-12d %-12.2f%n",
                    student.getLastName(), student.getFirstName(),
                    student.getStudentID(), student.getTotalMarks());
        }

        System.out.println("\nTop 5 students with the lowest total marks:");
        System.out.printf("%-20s %-20s %-12s %-12s%n", "Last Name", "First Name", "Student ID", "Total Marks");
        for (int i = 0; i < Math.min(5, students.size()); i++) {
            Student student = students.get(i);
            System.out.printf("%-20s %-20s %-12d %-12.2f%n",
                    student.getLastName(), student.getFirstName(),
                    student.getStudentID(), student.getTotalMarks());
        }
    }
}

class Student {
    private String lastName;
    private String firstName;
    private int studentID;
    private double a1;
    private double a2;
    private double a3;
    private double totalMarks;

    public Student(String lastName, String firstName, int studentID, double a1, double a2, double a3) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.studentID = studentID;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.totalMarks = 0.0;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getStudentID() {
        return studentID;
    }

    public double getA1() {
        return a1;
    }

    public double getA2() {
        return a2;
    }

    public double getA3() {
        return a3;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void calculateTotalMarks() {
        totalMarks = a1 + a2 + a3;
    }
}