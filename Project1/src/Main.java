import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  
    public static void main(String[] args) {
        String fileName, date;
        Scanner input = new Scanner(System.in);

		if (args.length < 2) {
            System.out.print("Enter the file name: ");
			fileName = input.nextLine();
        } else {
            fileName = args[1];
        }
			
        Pattern pattern = Pattern.compile("^(?:(?:31(\\/|-|\\.)" +
        "(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))" +
        "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?" +
        "(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$" +
        "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher; 

        do {
            System.out.print("Enter date of election in format mm/dd/yyyy: ");
		    date = input.nextLine();
            matcher = pattern.matcher(date);
        } while(!matcher.find());

        input.close();
    
        Scanner electionFile;
        try {
            electionFile = new Scanner(new FileInputStream("Project1/src/" + fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
            return;
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
		
        String electionType = electionFile.nextLine().strip();
        Election election;

        if(electionType.equals("IR")) {
            election = new IR_Election(electionFile);
        } else if(electionType.equals("CPL")) {
            election = new CPL_Election(electionFile);
        } else {
            System.out.printf("\"%s\" is not a valid election type\n", electionType);
            return;
        }
            
        election.run();
        electionFile.close();
    }
}