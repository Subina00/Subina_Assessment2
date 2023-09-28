import java.util.*;
import java.io.*;
/**
 * This is the assignment2 where we have to develop a simple program to compute statistics of students' marks in an assignment.
 *
 * @author (Subina)
 * @version (version 1.0 26/09/2023)
 */

public class Assignment2 {

// Defined data structure to store student information
    static class Student {
        String lastName, firstName, studentID, A1, A2, A3;
        double[] marks;

        public Student(String lastName, String firstName, String studentID, double[] marks) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.studentID = studentID;
            this.marks = marks;
        }
    
    }
    
// Function to read data from file
static List<Student> readDataFromFile(String fileName) throws IOException {
    List<Student> students = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    String line;

    while ((line = reader.readLine()) != null) {
        if (!line.startsWith("//")) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String lastName = parts[0];
                String firstName = parts[1];
                String studentID = parts[2];
                double[] marks = new double[parts.length - 3];

                for (int i = 3; i < parts.length; i++) {
                    try {
                        if (parts[i].isEmpty()) {
                            // Handle empty cells by considering them as 0
                            marks[i - 3] = 0.0;
                        } else {
                            marks[i - 3] = Double.parseDouble(parts[i]);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing marks for student: " + line);
                        break;
                    }
                }

                students.add(new Student(lastName, firstName, studentID, marks));
            } else {
                System.err.println("Skipped line with insufficient data: " + line);
            }
        }
    }

    reader.close();
    return students;
}

 public static void main(String[] args) {
        try {
            List<Student> students = readDataFromFile("Assignment.csv");

            // Menu System
            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\nMenu:");
                System.out.println("1. Calculate Total Marks");
                System.out.println("2. Print Students Below Threshold");
                System.out.println("3. Print Top 5 Students With Highest and Lowest Marks");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        printStudentsWithTotalMarks(students);
                        break;

                    case 2:
                        System.out.print("Enter the threshold: ");
                        double threshold = scanner.nextDouble();
                        printStudentsBelowThreshold(students, threshold);
                        break;

                    case 3:
                        printTopStudents(students);
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 4);

            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
 // Function to get total marks
        public double getTotalMarks() {
            double total = 0;
            for (double mark : marks) {
                total += mark;
            }
            return total;
        }
        

// Function to print students with total marks
 static void printStudentsWithTotalMarks(List<Student> students) {
        for (Student student : students) {
            System.out.println(student.lastName + ", " + student.firstName +
                    " (ID: " + student.studentID + ") - Assessment Marks: " +
                    Arrays.toString(student.marks) + " - Total Marks: " + student.getTotalMarks());
        }
    }

    

   
}
