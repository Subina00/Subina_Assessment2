import java.util.*;
import java.io.*;
/**
 * This is the assignment2.
 *
 * @author (Subina)
 * @version (a version number or a date)
 */
public class Assignment2
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Week5Token
     */
    public Assignment2()
    {
        // initialise instance variables

    }

    public void readFromFile() throws Exception {
        List<Student> students = new ArrayList<>();
        Scanner fileScanner = new Scanner(new File("prog5001_students_grade_2022.csv"));

        while (fileScanner.hasNextLine()) {

            String line = fileScanner.nextLine();

            if (line.startsWith("#")) { // Ignore comment lines
                continue;
            }

            String[] values = line.split(",");
            if (values.length != 6) { // Ensuring valid data ignores the empty cells replace this by changing the null values to 0
                continue;
            }

            String lastName = values[0].trim();
            String firstName = values[1].trim();
            String studentID = values[2].trim();
            try{
                double mark1 = Double.parseDouble(values[3].trim());
                Student student = new Student(firstName,lastName, studentID, mark1);
                students.add(student);
            }catch(NumberFormatException e){
                System.out.println( "");
            }
        }

        fileScanner.close();
        printStudents(students);
    }

    public  void printStudents(List<Student> students) {
        for (Student s : students) {
            System.out.println( s.firstName+" "+" "+s.studentID+ " "+s.assign1);
        }
    }



}
