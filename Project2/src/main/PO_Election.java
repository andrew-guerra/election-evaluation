package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/** 
 * A class that represents an PO_Election and contains methods
 * to run the entirity of PO_Election, and display
 * the winner of an PO_Election.
 * 
 * @Authors Andrew Guerra
 */
public class PO_Election extends Election {
    private Candidate[] candidates;
    private int numCandidates;
    private PO_Ballot[] ballots;
    private int ballotCount;
    private int[] filesBallotCount;
    private Candidate winningCandidate;
    
    /**
     * Instantiates a PO_Election object. It is passed a Scanner object array of an election files to retrieve election data, and also 
     * a String represenation of the date of the election.
     * The constructor calls two private methods, readPOHeader() and readPOBallots() to set up the PO_Election.
     * 
     * @param electionFiles
     * @param date
     */
    public PO_Election(Scanner[] electionFiles, String date) {
        super(electionFiles);  
        filesBallotCount = new int[electionFiles.length];

        readPOHeader();
        setFileScanners();
        readPOBallots();
    }

    /**
     * Instantiates a PO_Election object. It is passed a Scanner object of an election file to retrieve election data, and also 
     * a String represenation of the date of the election.
     * The constructor calls two private methods, readPOHeader() and readPOBallots() to set up the PO_Election.
     * 
     * @param electionFile
     * @param date
     */
    public PO_Election(Scanner electionFile, String date) {
        super(electionFile);
        
        readPOHeader();
        readPOBallots();
    }

    /**
     * Reads header information from an PO_Election.csv file. Assumes the electionFile Scanner
     * is at the second line of a PO_Election ballot file. Updates an PO_elections list of candidates,
     * number of candidates,  ballots, and ballot count information.
     * Addidtally initalizes each candidates names, party, ballot count, and list of ballots.
     * Takes no parameters and returns nothing. Used as a helper function for run().
     */
    private void readPOHeader() {
        if(electionFiles != null) {
            electionFile = electionFiles[0];
        }

        // read second line of file and get the number of candidates
        // and move scanner to the next line with candidates afterwards
        numCandidates = electionFile.nextInt();
        electionFile.nextLine();

        // create an array of candidates
        candidates = new Candidate[numCandidates];

        String name, party;

        // loop for the total number of candidates and fill in candidates fields for each candidate
        for(int i = 0; i < numCandidates; i++) {
            // get name of candidate;
            name = electionFile.next();
            name = name.substring(1, name.length() - 1);
            
            //get party of candidate   
            party = electionFile.next();

            //get party name
            if(name.charAt(name.length() - 1) == ',') {
                party = party.substring(0, party.length() - 2);
            } else {
                party = party.substring(0, party.length() - 1);
            }
            
            //fill fields of candidates
            candidates[i] = new Candidate();
            candidates[i].setBallotCount(0);
            candidates[i].setBallots(null);
            candidates[i].setName(name);
            candidates[i].setParty(party);
        }
        electionFile.nextLine();

        // get the total number of Ballots
        numBallots = electionFile.nextInt();

        if(electionFiles != null) {
            filesBallotCount[0] = numBallots;
        }

        // scanner, now on lines with ballots
        electionFile.nextLine();
        return;
    }

    /**
     * Helper method for readPOBallots(). Sets the Scanner object of each file to the place to read in ballots. 
     * Initializes the numBallotsFile[] array. Assumes Scanner objects are pointing at first line of a PO.csv file.
     * Takes no parameters and returns nothing
     */
    private void setFileScanners() {
        if(electionFiles != null) {
            for (int i = 0; i < electionFiles.length; i++) {     // iterate through Scanner objects for files
                Scanner electionFile = electionFiles[i];
                if (i != 0) {                                    // do not read in Scanner used in PO_Header
                    electionFile.nextLine();
                    electionFile.nextLine();
                    electionFile.nextLine();

                    int fileBallots = electionFile.nextInt();
                    numBallots = numBallots + fileBallots;       // add to total number of ballots
                    filesBallotCount[i] = fileBallots;             // add to file total of ballots
                    electionFile.nextLine();                     // Scanner ready to read in ballots
                }
            }
        }
    }

    /**
     * Helper method for run(). Reads ballot information from a PO_Election.csv file. Assumes readPOHeader() has been
     * previously called and Scanner is ready to read in the first ballot in the file. Creates a PO_Ballot for each ballot read
     * in and stores in an array of PO_Ballots.
     * Takes no parameters and returns nothing.
     */
    private void readPOBallots() {
        int index = 0;
        ballots = new PO_Ballot[numBallots]];

        if(electionFiles != null) {
            for(int i = 0; i < electionFiles.length; i++) {            // iterate through files
                Scanner electionFile = electionFiles[i];
                String ballot;
                
                for(int j = 0; j < filesBallotCount[i]; j++) {           // iterate through a file and create ballots
                    ballot = electionFile.nextLine();
                    int partyNum = ballot.indexOf("1");                 // partyNum is index into parties[]
                    PO_Ballot temp = new PO_Ballot(partyNum, index);
                    ballots[index] = temp;                       // store ballot in system
                    index++;
                }
            }
        } else {
            String ballot;
            for(int j = 0; j < numBallots; j++) {                       // iterate through a file and create ballots
                ballot = electionFile.nextLine();
                int partyNum = ballot.indexOf("1");                     // partyNum is index into parties[]
                PO_Ballot temp = new PO_Ballot(partyNum, index);
                ballots[index] = temp;                                  // store ballot in system
                index++;
            }
        }
    }

    /** 
     * Runs the PO algorithm and writes audit information to the audit file and displays the results and winner
     * information of the eleciton to the screen. Assumes the electionFile Scanner object begins on the second line, all
     * information contained in the CPL election ballot file is entered properly, there is at least one ballot in the file and 
     * at least one candidate. Takes no parameters and returns nothing. Generates an audit file.
     * 
     * Assumes election class has been initialized properly
     */
    public void run() {
        shuffleBallots();                          // shuffle ballots
        allocateBallots();
        allocateSeat();    
    }

    /**
     * Shuffles the array of ballots contained in PO_Election.
     * Takes no parameters and returns nothing.
     */
    public void shuffleBallots() {
        Random rnd = ThreadLocalRandom.current();
        for(int i = ballots.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            PO_Ballot a = ballots[index];            // swap ballots
            ballots[index] = ballots[i];
            ballots[i] = a;
        }
    }

    /**
     * Allocates ballots to corresponding candidates
     */
    public void allocateBallots() {
        for(int i = 0; i < numBallots; i++) {
            candidates[ballots[i].getPartyNum()].incrementBallotCount();  
        }
    }

    /**
     * Allocate winning seat to winning candidate based on allocated ballots
     */
    public void allocateSeat() {
        ArrayList<Candidate> leadingCandidates = new ArrayList<>();
        int highestVoteCount = -1;

        for(Candidate candidate : candidates) {
            if(candidate.getBallotCount() > highestVoteCount) {
                leadingCandidates = new ArrayList<>();
                leadingCandidates.add(candidate);
                highestVoteCount = candidate.getBallotCount();
            } else if(candidate.getBallotCount() == highestVoteCount) {
                leadingCandidates.add(candidate);
            }
        }

        if(leadingCandidates.size() != 0) {
            winningCandidate = coinToss(leadingCandidates);
        }
    }

    /** 
     * Returns random Candidate from list of candidates in a simulated coin toss
     * @param tiedParties   an ArrayList of Candidate objects that have tied 
     * @return              winning Candidate of coin toss
     */
    private Candidate coinToss(ArrayList<Candidate> tiedCandidates) {
        Random random = new Random();
        int randomNumber = random.nextInt(tiedCandidates.size());
        return tiedCandidates.get(randomNumber);
    }

    /**
     * Retrurns winning candidate of election
     * 
     * @return winning candidate
     */
    public Candidate getWinningCandidate() {
        return winningCandidate;
    }
}
