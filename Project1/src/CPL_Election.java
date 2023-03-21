import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPL_Election extends Election {

    private int numParties;
    private int numSeats;
    private int availableSeats;
    private int numBallots;

    private CPL_Audit audit;

    private CPL_Ballot[] initialBallots;
    private Party[] parties;


    public CPL_Election(Scanner electionFile) {
        super(electionFile);
        this.readCPLHeader();
        this.readCPLBallots();
    }
    public void run() {
        this.shuffleBallots();
        /*
        System.out.println(this.getNumParties());
        for (int i = 0; i < this.getNumParties(); i++) {
            System.out.println(parties[i].getPartyName() + " - " + parties[i].getCandidateList());
        }
        System.out.println(this.getNumBallots()); 
        */
    }
    private ArrayList<Party> setFirstSeatsAllocation() {
        return null;
    }
    private void setRemainderSeatsAllocation(ArrayList<Party> partiesAvailable) {

    }
    private void shuffleBallots() {
        Random rnd = ThreadLocalRandom.current();
        for (int i = initialBallots.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            CPL_Ballot a = initialBallots[index];       // swap ballots
            initialBallots[index] = initialBallots[i];
            initialBallots[i] = a;
        }
    }
    private void readCPLHeader() {
        
        int numParties = electionFile.nextInt();        // get number of parties from header
        this.setNumParties(numParties);
        parties = new Party[numParties];
                    
        electionFile.nextLine();                        // set scanner to read party names
        String temp = electionFile.nextLine();
        
        for (int i = 0; i < numParties; i++) {          // create party objects
            String partyName;
            int index = temp.indexOf(",");
            if (index != -1) {                          // case where >1 parties remain
                partyName = temp.substring(0, index);   
                temp = temp.substring(index + 2);
            } else {                                    // case where one party remains
                partyName = temp;
            }
             
            Party partyAdd = new Party(partyName);      // add party to list
            parties[i] = partyAdd;
        }
  
        String candidateList;                           // String to hold comma deliminated candidates
        int numCandidates;

        for (int i = 0; i < numParties; i++) {          // read candidate names for each party
            numCandidates = 0;
            candidateList = electionFile.nextLine();
            parties[i].setCandidateList(candidateList);
            parties[i].setNumCandidate(numCandidates);
        }
        this.setNumSeats(electionFile.nextInt());       // get number of seats
        electionFile.nextLine();
        this.setNumBallots(electionFile.nextInt());     // get number of ballots
        electionFile.nextLine();

    }
    private void readCPLBallots() {

        String ballot;
        initialBallots = new CPL_Ballot[this.getNumBallots()];
        for (int i = 0; i < this.getNumBallots(); i++) {     // create ballots
            ballot = electionFile.nextLine();
            int partyNum = ballot.indexOf("1");              // partyNum is index into parties[]
            CPL_Ballot temp = new CPL_Ballot(partyNum, i);
            initialBallots[i] = temp;
        }
    }
    public int getNumParties() {
        return numParties;
    }
    public void setNumParties(int numParties) {
        this.numParties = numParties;
    }
    public int getNumSeats() {
        return numSeats;
    }
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }
    public int getNumBallots() {
        return numBallots;
    }
    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }
}