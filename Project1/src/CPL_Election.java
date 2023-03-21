import java.util.ArrayList;
import java.util.Scanner;
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
    }
    public void run() {

    }
    private ArrayList<Party> setFirstSeatsAllocation() {
        return null;
    }
    private void setRemainderSeatsAllocation(ArrayList<Party> partiesAvailable) {

    }
    public void readCPLHeader() {

        
        int numParties = electionFile.nextInt();        // get number of parties from header
        this.setNumParties(numParties);
        parties = new Party[numParties];
                    
        electionFile.nextLine();                        // set scanner to read party names

        for (int i = 0; i < numParties; i++) {          // create party objects

            String partyName = electionFile.next();     // get party name

            partyName = partyName.substring(0, partyName.length() - 1);     // remove comma
            Party temp = new Party(partyName);
            parties[i] = temp;
        }
        electionFile.nextLine();

        String candidateList = "";                      // String to hold comma deliminated candidates
        int numCandidates = 0;

        for (int i = 0; i < numParties; i++) {          // read candidate names for each party
            while(electionFile.hasNext()) {             // candidates for party
                numCandidates++;
                String candidateName = electionFile.next();         // get candidate name
                candidateList = candidateList + candidateName;      // remove comma
            }
            electionFile.nextLine();
            parties[i].setCandidateList(candidateList);
            parties[i].setNumCandidate(numCandidates);
        }
        this.setNumSeats(electionFile.nextInt());       // get number of seats
        electionFile.nextLine();
        this.setNumBallots(electionFile.nextInt());     // get number of ballots
        electionFile.nextLine();

    }
    public void readCPLBallots() {

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