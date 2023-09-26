import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
/**
 * Write a description of class Fil here.
 *
 * @author (Subina)
 * @version (a version number or a date)
 */
public class Assignment2
{
    // instance variables - replace the example below with your own
    private File myFile;
    
    //call methods in constructor,nothing else
    public Assignment2()
    {
        
  
        writeToFile();
        readFromFile();
        
    }
    
    
    //writing to a file
    public void writeToFile(){
    try{
            File myFile = new File ("prog5001_students_grade_2022.csv");
            FileWriter myWriter = new FileWriter(myFile);
            myWriter.write ("Files in Java are really easy!");
            myWriter.close();
        }
        catch(IOException e){
            System.out.println ("Error");
            e.printStackTrace();
        }
    }
    
    //reading contents of the file
    public void readFromFile(){
         try{
        File myFile = new File("prog5001_students_grade_2022.csv");
        Scanner myScanner = new Scanner(myFile);
        while (myScanner.hasNextLine()){
            String line = myScanner.nextLine();
         System.out.println(line); // Print each line from the file
            //  runMethod (userOption);
              }
              myScanner.close();
        }
        catch(IOException e){
            System.out.println ("Error");
            e.printStackTrace();
        }
    
    }
    
   // public void runMethod(){
    //if (userOption == 1){readFromFile();}
    //else if (userOption == 2) {writeToFile();}
    //else if (userOption == 3) ;{createFile();}
    //}
}