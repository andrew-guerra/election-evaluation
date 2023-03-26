package Project1.src;

import java.util.Locale;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Main is a class that handles user input, UI, and running of IR and CPL elections
 * 
 * @author  Andrew Guerra
 */
public class Main {
  
    /**
     * Retrives the filename from either the command line arugments or user input.
     * 
     * @param args      String command line arguments
     * @param input     Scanner for user input
     * @return
     */
    public static String retrieveFilename(String[] args, Scanner input) {
        if (args.length < 1) {
            System.out.print("Enter the file name: ");
			return input.nextLine();
        }
        
        return args[0];
    }

    /**
     * Generates a Scanner object based on file name. File name is assumed relative to Project1/src. 
     * Returns null when an exception occurs.
     * 
     * @param fileName  file name for election file
     * @return          Scanner object for election file 
     */
    public static Scanner loadElectionFile(String fileName) {
        Scanner electionFile;

        try {
            electionFile = new Scanner(new FileInputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return electionFile;
    }

    /**
     * Retrieves and validates String date from the user.
     * 
     * @param input     Scanner for user input
     * @return          String of date retrieved from user      
     */
    public static String retrieveDate(Scanner input) {
        // generate date format based on local US time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT);
        String dateStr;

        do {
            System.out.print("Enter date of election in format mm-dd-yyyy: ");
            dateStr = input.nextLine();
            
            try {
                // use parse error to check for properly formated date
                dateFormatter.parse(dateStr);
            } catch(DateTimeParseException e) {
                continue;
            }

            break;
        } while(true);

        return dateStr;
    }

    /**
     * Retrieves and validates election type in election file and generates an election object based on that election type.
     * Returns null when an invalid election type is retrieved.
     * 
     * @param electionFile  Scanner of election file
     * @return              Election object of election type in election file
     * @throws IOException
     */
    public static Election retrieveElection(Scanner electionFile, String date) throws IOException {
        String electionType = electionFile.nextLine().strip();
        if(electionType.equals("IR")) {
            return new IR_Election(electionFile, date);
        } else if(electionType.equals("CPL")) {
            return new CPL_Election(electionFile, date);
        }
        
        System.out.printf("\"%s\" is not a valid election type\n", electionType);
        return null;
    }

    public static void main(String[] args) throws IOException {
        String fileName, dateStr;
        Scanner electionFile, input;
        Election election;

        input = new Scanner(System.in);
		if((fileName = retrieveFilename(args, input)) == null) {
            input.close();
            return;
        }
	
        if((electionFile = loadElectionFile(fileName)) == null) {
            input.close();
            return;
        }    
        
        if((dateStr = retrieveDate(input)) == null) {
            input.close();
            return;
        }
        
        input.close();
	
        if((election = retrieveElection(electionFile, dateStr)) == null) {
            electionFile.close();
            return;
        }
            
        election.run();
        electionFile.close();
    }
}