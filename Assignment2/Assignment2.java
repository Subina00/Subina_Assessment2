import java.util.*;
import java.io.*;

public class Assignment2 {

    // Inner class to represent a Student
    static class Student {
        String lastName, firstName, studentID;
        double[] marks;

        // Constructor for creating a Student object
        public Student(String lastName, String firstName, String studentID, double[] marks) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.studentID = studentID;
            this.marks = marks;
        }

        // Method to calculate and return the total marks for a student
        public double getTotalMarks() {
            return Arrays.stream(marks).sum();
        }

        // Method to calculate and return the GPA for a student
        public double calculateGPA() {
            double totalMarks = getTotalMarks();
            double gpa = (totalMarks / 100) * 4;
            return gpa;
        }
    }

    /**
     * Reads student data from a CSV file and returns a list of Student objects.
     *
     * @param fileName The name of the CSV file to read.
     * @return A list of Student objects.
     * @throws IOException If there is an error reading the file.
     */
    static List<Student> readDataFromFile(String fileName) throws IOException {
        List<Student> students = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
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
        } catch (IOException e) {
            throw e; // Re-throw the exception for handling in the main method
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Handle any error during closing of the file
                    System.err.println("Error while closing the file: " + e.getMessage());
                }
            }
        }

        return students;
    }

    /**
     * Main method to run the Assignment2 program.
     * This method performs the following tasks:
     * 1. Reads student data from a CSV file.
     * 2. Displays a menu for the user to choose various operations.
     * 3. Based on the user's choice, it calls different methods to calculate statistics or exit.
     * 4. Handles exceptions for file reading errors.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        boolean fileReadError = false; // Flag to track file read errors

        // Loop until a valid file name is provided or user chooses to exit
        while (true) {
            // Prompt the user for the CSV file name
            System.out.print("Enter the CSV file name: ");
            fileName = scanner.nextLine();

            try {
                List<Student> students = readDataFromFile(fileName);

                // Reset the fileReadError flag if the file was successfully read
                fileReadError = false;

                // Menu System
                int choice;
                do {
                    System.out.println("\nMenu:");
                    System.out.println("1. Read from file");
                    System.out.println("2. Calculate the Total Marks of Students");
                    System.out.println("3. Print Students Scoring Less Than the Threshold");
                    System.out.println("4. Print Top 5 Students With Highest and Lowest Total Marks");
                    System.out.println("5. Exit");
                    System.out.print("Please select an option (1/2/3/4/5): ");

                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid option (1/2/3/4/5): ");
                        scanner.next();
                    }

                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            readAndPrintStudentsFromFile(students, fileName);
                            break;

                        case 2:
                            printStudentsWithTotalMarks(students);
                            break;

                        case 3:
                            double threshold;
                            do {
                                System.out.print("Specify the Threshold: ");
                                while (!scanner.hasNextDouble()) {
                                    System.out.println("Invalid input. Please enter a valid threshold: ");
                                    scanner.next();
                                }
                                threshold = scanner.nextDouble();
                                scanner.nextLine(); // Consume newline
                            } while (threshold < 0.0 || threshold > 100.0);

                            printStudentsBelowThreshold(students, threshold);
                            break;

                        case 4:
                            printTopStudents(students);
                            break;

                        case 5:
                            System.out.println("Exiting...");
                            break;

                        default:
                            System.out.println("Invalid selection. Please choose a valid option.");
                            break;
                    }
                } while (choice != 5);

                break; // Exit the loop if valid data is read

            } catch (FileNotFoundException e) {
                System.err.println("Error: The CSV file '" + fileName + "' was not found. Please try again.");
                fileReadError = true; // Set the fileReadError flag
            } catch (IOException e) {
                System.err.println("File reading error: " + e.getMessage() + " Please try again.");
                fileReadError = true; // Set the fileReadError flag
            }
        }

        scanner.close();
    }

    /**
     * Prints the details of students along with their total marks and GPA.
     *
     * @param students The list of Student objects to print.
     */
    static void printStudentsWithTotalMarks(List<Student> students) {
        for (Student student : students) {
            System.out.print(student.lastName + ", " + student.firstName +
                    " (ID: " + student.studentID + ") - Assignment Marks: [");

            for (int i = 0; i < student.marks.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(student.marks[i]);
            }

            System.out.println("] - Total Marks: " + student.getTotalMarks() +
                    " - GPA: " + student.calculateGPA());
        }
    }

    /**
     * Prints students with total marks below a specified threshold.
     *
     * @param students  The list of Student objects to print.
     * @param threshold The threshold for total marks.
     */
    static void printStudentsBelowThreshold(List<Student> students, double threshold) {
        System.out.println("Students Scoring Less Than the Threshold (Threshold: " + threshold + "):");
        boolean found = false;

        for (Student student : students) {
            if (student.getTotalMarks() < threshold) {
                found = true;
                System.out.println(student.lastName + ", " + student.firstName +
                        " (ID: " + student.studentID + ") - Assignment Marks: " +
                        Arrays.toString(student.marks) + " - Total Marks: " + student.getTotalMarks());
            }
        }

        if (!found) {
            System.out.println("No students found below the specified threshold.");
        }
    }

    /**
     * Prints the top 5 students with the highest and lowest total marks.
     *
     * @param students The list of Student objects to print.
     */
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

    /**
     * Reads and prints student data from a file, indicating last name, first name,
     * student ID, and marks. Empty cells are considered as 0.0.
     *
     * @param students The list of Student objects to print.
     * @param fileName The name of the CSV file to read.
     */
    static void readAndPrintStudentsFromFile(List<Student> students, String fileName) {
        System.out.println("Reading and printing students from file: " + fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("//")) {
                    String[] parts = line.split(",");
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].isEmpty()) {
                            parts[i] = "0.0";
                        }
                    }
                    // Print last name, first name, student ID, and marks
                    System.out.println("Last Name: " + parts[0] +
                                       ", First Name: " + parts[1] +
                                       ", Student ID: " + parts[2] +
                                       ", Marks: " + Arrays.toString(Arrays.copyOfRange(parts, 3, parts.length)));
                }
            }
        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
        }
    }
}