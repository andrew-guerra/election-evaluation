//package Project1.src;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class IR_Audit{

    

    // need to have type of voting
    // print number of candidates 
    // print out candiate names 
    // print out total number of ballots
    // dont need calculations 
    // print out how many votes a candidate has 
    // print out winner name
    // print out how the election progressed 
    // show the order of removal of candidates and what ballots were redistributed

    private String date; 
    private File auditFile; 
    private FileWriter auditWriter; 
    
    public static void main(String[] args) {
        File audit = new File("audit_test.txt");
        FileWriter auditWriter;
        String date = "1/1/1";
        try {
            auditWriter = new FileWriter("audit_test.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("crying");
            return;
        } 
        
        Scanner electionFile;
        
        try {
            electionFile = new Scanner(new File("Project1/src/header.csv"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("crying2");
            return;
        }

        electionFile.next();
        electionFile.nextLine();

        IR_Election ir = new IR_Election(electionFile);
        IR_Audit auditIR = new IR_Audit(date, audit, auditWriter);

    }
    
    
    // do we create the audit object in main, do we create it in IR_Election
    public IR_Audit(String date, File auditFile, FileWriter auditWriter {
        this.date = date;
        this.auditFile = auditFile;
        this.auditWriter = auditWriter;
    }

    public IR_Audit() {
        this.date = "";
        this.auditFile = null;
        this.auditWriter = null;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public File getAuditFile() {
        return this.auditFile;
    }

    public void setAuditFile(File auditFile) {
        this.auditFile = auditFile;
    }

    public Scanner getScanner() {
        return this.auditWriter;
    }

    public void setScanner(Scanner auditIR) {
        this.auditIR = auditIR;
    }

    public void writeHeaderToFile(IR_Election election){
        //print out type of voting
        String output = election.getTypeElection();
        //System.out.println(output);

        //print out number of candiate
        int numCandidates = election.getNumCandidates(); 
        //write to file 

        //print out names 
        for (int i = 0; i < election.getNumCandidates() - 1; i++) {
            //System.out.println(election.getCandidates()[i].getName() + " " +
            //election.getCandidates()[i].getParty());
        } 

        //print out total number of ballots
        int numBallots = election.getNumBallots();
        //write to the file. 



    }
    


    
}

    
