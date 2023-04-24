package main;

import java.util.Scanner;

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
    private IR_Ballot[] ballots;
    private int ballotCount;
    
    
    /**
     * Constructor for PO_Election. Initalizes fields
     * @param electionFile is a Scanner that should read from
     * a IR_Election ballot file. Scanner Should point at the second line
     * of the file or else other methods may fail.
     * @param date is a string that represent the date at which
     * the election was run
     */
    public PO_Election(Scanner electionFile, String date) {
        super(electionFile);
        this.typeElection = "IR Election";
        candidates = null;
        numCandidates = 0;
        ballots = null;
        ballotCount = 0;
    }

    /**
     * Reads header information from an PO_Election.csv file. Assumes the electionFile Scanner
     * is at the second line of a PO_Election ballot file. Updates an PO_elections list of candidates,
     * number of candidates,  ballots, and ballot count information.
     * Addidtally initalizes each candidates names, party, ballot count, and list of ballots.
     * Takes no parameters and returns nothing. Used as a helper function for run().
     */
    private void readPOHeader() {
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

        // scanner, now on lines with ballots
        electionFile.nextLine();
        return;
    }
}
