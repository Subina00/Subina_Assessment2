import java.util.*;
import java.io.*;
/**
 * This is the assignment2 where we have to develop a simple program to compute statistics of students' marks in an assignment.
 *
 * @author (Subina)
 * @version (version 1.0 26/09/2023)
 */

public class Assignment2 {

static class Student {
    String lastName, firstName, studentID;
    double[] marks;

    public Student(String lastName, String firstName, String studentID, double[] marks) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.studentID = studentID;
        this.marks = marks;
        }

    public double getTotalMarks() {
            return Arrays.stream(marks).sum();
    }
    }

public static void main(String[] args) {
    try {
        List<Student> students = readDataFromFile("Studentsfile.csv");

        // Menu System
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Calculate the Total Marks of Students");
            System.out.println("2. Print Students Scoring Less Than the Threshold");
            System.out.println("3. Print Top 5 Students With Highest and Lowest Total Marks");
            System.out.println("4. Exit");
            System.out.print("Enter a number to proceed: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printStudentsWithTotalMarks(students);
                    break;

                case 2:
                    System.out.print("Specify the Threshold: ");
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
                    System.out.println("Incorrect selection. Please retry.");
                    break;
                }
            } while (choice != 4);

        scanner.close();
        } catch (IOException e) {
        System.out.println("File reading error: " + e.getMessage());
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
                        System.err.println("Error in processing student marks: " + line);
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

// Function to print students with total marks
static void printStudentsWithTotalMarks(List<Student> students) {
for (Student student : students) {
    System.out.print(student.lastName + ", " + student.firstName +
            " (ID: " + student.studentID + ") - Assignment Marks: [");
        
    // Print marks with "0.0" for empty cells
    for (int i = 0; i < student.marks.length; i++) {
        if (i > 0) {
            System.out.print(", ");
        }
        System.out.print(student.marks[i]);
        }

    System.out.println("] - Total Marks: " + student.getTotalMarks());
    }
}

// Function to print students with total marks below threshold
static void printStudentsBelowThreshold(List<Student> students, double threshold) {
    System.out.println("Students Scoring Less Than the Threshold:");
    for (Student student : students) {
        if (student.getTotalMarks() < threshold) {
            System.out.println(student.lastName + ", " + student.firstName +
                    " (ID: " + student.studentID + ") - Assignment Marks: " +
                    Arrays.toString(student.marks) + " - Total Marks: " + student.getTotalMarks());
            }
        }
    }

// Function to print top 5 students with highest and lowest total marks
static void printTopStudents(List<Student> students) {
    students.sort(Comparator.comparingDouble(Student::getTotalMarks).reversed());

    System.out.println("Top 5 Students with Highest Total Marks:");
    for (int i = 0; i < Math.min(5, students.size()); i++) {
        Student student = students.get(i);
        System.out.println(student.lastName + ", " + student.firstName +
                " (ID: " + student.studentID + ") - Assignment Marks: " +
                Arrays.toString(student.marks) + " - Total Marks: " + student.getTotalMarks());
        }

    System.out.println("\nTop 5 Students with Lowest Total Marks:");
    for (int i = 0; i < Math.min(5, students.size()); i++) {
        Student student = students.get(students.size() - 1 - i);
        System.out.println(student.lastName + ", " + student.firstName +
                " (ID: " + student.studentID + ") - Assignment Marks: " +
                Arrays.toString(student.marks) + " - Total Marks: " + student.getTotalMarks());
        }
    }
} 