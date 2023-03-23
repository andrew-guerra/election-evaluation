import java.io.*;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("/home/grimm223/Desktop/5801Proj/repo-Team21/Project1/src/header.csv"));
        sc.useDelimiter(","); // sets the delimiter pattern
        while (sc.hasNext()) // returns a boolean value
        {
            System.out.print(sc.next() + ","); // find and returns the next complete token from this scanner
        }
        sc.close(); // closes the scanner
    }
}