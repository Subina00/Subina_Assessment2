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
        // Function to get total marks
        public double getTotalMarks() {
            double total = 0;
            for (double mark : marks) {
                total += mark;
            }
            return total;
        }
    }
}
