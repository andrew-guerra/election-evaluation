package main;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JTable;

import java.util.Random;

/** 
 * A class that represents an IR_Election and contains methods
 * to run the entirity of IR_Election, produce an audit file, and display
 * the winner of an IR_Election.
 * 
 * @Authors Hady Kotifani
 */
public class IR_Election extends Election {

    private Candidate [] candidates;
    private int numCandidates;
    private int numRemainingCandidates;
    private IR_Ballot [] currentBallots;
    private int currentBallotCount;
    private IR_Audit audit; 
    private int numBallotsFile[];
    private IR_Table ir_table;

    
    /**
     * Constructor for IR_Election. Initalizes fields
     * @param electionFile is a Scanner that should read from
     * a IR_Election ballot file. Scanner Should point at the second line
     * of the file or else other methods may fail.
     * @param date is a string that represent the date at which
     * the election was run
     */
    public IR_Election(Scanner electionFile, String date) {
        super(electionFile);
        this.typeElection = "IR Election";
        audit = new IR_Audit(date);
        candidates = null;
        numCandidates = 0;
        numRemainingCandidates = 0;
        currentBallots = null;
        currentBallotCount = 0;
    }

     /**
     * Constructor for IR_Election. Initalizes fields
     * @param electionFiles is a Scanner that should read from
     * a IR_Election ballot file. Scanner Should point at the second line
     * of the file or else other methods may fail.
     * @param date is a string that represent the date at which
     * the election was run
     */
    public IR_Election(Scanner[] electionFiles, String date) {
        super(electionFiles);
        this.typeElection = "IR Election";
        audit = new IR_Audit(date);
        candidates = null;
        numCandidates = 0;
        numRemainingCandidates = 0;
        currentBallots = null;
        currentBallotCount = 0;
        numBallotsFile = null;
    }

    /**
     * Reads header information from an IR_Election.csv file. Assumes the electionFile Scanner
     * is at the second line of a IR_Election ballot file. Updates an IR_elections list of candidates,
     * number of candidates, number of remaining candidates, current ballots, and current ballot count
     * information. Addidtally initalizes each candidates names, party, ballot count, and list of ballots.
     * Takes no parameters and returns nothing. Used as a helper function for run().
     */
    private void readIRHeader() {
        // read second line of file and get the number of candidates
        // and move scanner to the next line with candidates afterwards
        numCandidates = electionFile.nextInt();
        electionFile.nextLine();

        // number of candidates still in the running, set equal to total number of candidates
        numRemainingCandidates = numCandidates;

        // create an array of candidates
        candidates = new Candidate[numCandidates];

        // loop for the total number of candidates and fill in candidates fields for each candidate
        for (int i = 0; i < numCandidates; i++) {
            // get name and party of candidate
            String name = electionFile.next();
            String party = electionFile.next();

            //get party name in form of "(*)" where * is letter of the party and remove comma at end if present
            if (party.charAt(party.length() - 1) == ',') {
                party = party.substring(0, party.length() - 1);
            } else {
                party = party.substring(0, party.length());
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

        // set the current number of undistributed ballots to equal the total number of ballots
        currentBallotCount = numBallots;

        // scanner, now on lines with ballots
        electionFile.nextLine();
        return;
    }
    

    /**
     * Reads ballot information from an IR_Election.csv file and initalizes corresponding fields.
     * Assumes electionScanner is at the first line correlating to a ballots form information.
     * Creates a series of ballot objects and initalizes each ballot object using a ballot's form information.
     * Updates the IR_Elections ballots list to include each ballot object and initalizes
     * each candidate to have an array of ballots that can hold the total number of ballots.
     * Takes no parameters and returns nothing. Used as a helper function for run().
     * @throws PrinterException
     */
    private void readIRBallots() throws PrinterException {
        // create an array of ballots equal to the number of total ballots in the file
        IR_Ballot[] ballots = new IR_Ballot[numBallots];

        // loop over all ballots in the file and create a ballot for each ballot and add to total list of undistributed ballots
        for (int i = 0; i < numBallots; i++) {
            
            // ballot num goes from 0 to total - 1, for 6 ballots, numbered 0 to 5

            // create a new ballot object and initalize rank, ballot number, and form
            IR_Ballot ballot = new IR_Ballot();
            ballot.setRank(0);
            ballot.setBallotNum(i);
            ballot.setForm(electionFile.next());

            // create and initalize a candidate Ranking Array
            int[] candidateRanking = new int[numCandidates];

            // initalize the rankings for each candidate to be -1
            for (int k = 0; k < numCandidates; k++) {
                candidateRanking[k] = -1;
            }

            // using form, update candidate ranking array. Position correlates to candidate number
            // where position = 0, correlates to candidates[0]
            int position = 0;

            // loop over every character of the ballots form. example of form format: "1,,2,3"
            for (int j = 0; j < ballot.getForm().length(); j++) {
                // get current character
                char curChar = ballot.getForm().charAt(j);

                // check if the character is a ranking or a comma
                if (curChar == ',') {
                    // update position correlating to candidate number if a comma
                    position++;
                } else {
                    // get the candidates ranking of the candidates corresponding position/index
                    String ranks = "";
                    ranks += curChar;
                    //add letters if number is double digits
                    if ((j + 1) < ballot.getForm().length()) {
                        while (((j + 1) < ballot.getForm().length()) && (ballot.getForm().charAt(j + 1) != ',')) {
                            j++;
                            curChar = ballot.getForm().charAt(j);
                            ranks += curChar;
                        }
                    }
                    int rank = Integer.parseInt(ranks);

                    // the candidates indexes are sorted in the candidate ranking array
                    // according to their rank, ex. a form "3,,1,2" would result in "{2,3,0, -1, -1}",
                    // candidate with index 2 is ranked first and candidate with index 0 is ranked third.
                    // Set candidates ranking at [rank -1] (since arrays start at 0), and set it equal
                    // to the candidates position in the candidates array
                    candidateRanking[rank - 1] = position;
                }
            }

            // set ballots candidate ranking
            ballot.setCandidateRanking(candidateRanking);
            
            // add the newly made and initalized ballot to the list of ballots
            ballots[i] = ballot;
        }

        // update IR_Elections current unallocated ballots to equal the list of processed ballots
        // read in from the file
        currentBallots = ballots;

        // initalize an empty array of ballots for each candidate correlating
        // to the size of the total possible number ballots a candidate can have
        for (int i = 0; i < numCandidates; i++) {
            IR_Ballot [] ballotbox = new IR_Ballot[numBallots];
            candidates[i].setBallots(ballotbox);
        }

        
        return;
    }


    /**
     * Allocates all unallocated ballots contained in IR_Election to its respective candidate based 
     * on the ballots ranking of candidates. Only gives the candidate the ballot if they are still in the
     * running, elsewise the ballot is discarded. Takes no parameters and returns nothing.
     * Used as a helper function for run().
     */
    private void allocateBallots() {
        // loop over all ballots that have not been allocated
        // and exist in IR_Election
        for (int i = 0; i < currentBallotCount; i++) {

            
            // get the candidate number that the ballot should go to
            // use the ballots current rank position
            IR_Ballot ballotToAllocate = currentBallots[i];
            int currentBallotRank = ballotToAllocate.getRank();
            int candidateNum = ballotToAllocate.getCandidateAtNum(currentBallotRank);
            while(candidateNum != -1 && candidates[candidateNum] == null) {
                currentBallotRank++;
                ballotToAllocate.setRank(currentBallotRank);
                candidateNum = ballotToAllocate.getCandidateAtNum(currentBallotRank);
            }
            
            // give ballot to candidate if he is still in the running and ballot
            // is ranked up there
            if (candidateNum != -1 && candidates[candidateNum] != null) {
                candidates[candidateNum].addBallot(ballotToAllocate);
            }
        }

        

        // IR_Election has distributed all ballots in position
        // so current ballots is set to null and current ballot count
        // set to 0
        currentBallots = null;
        currentBallotCount = 0;
        return;
    }


    /**
     * Determines the index of the winning candidate who won a coin toss.
     * Used as a helper function for findLowestCandidate() and runPopularity().
     * @param tieFolk   an array of ints that correlates to the index number of candidates that have ties 
     * @return          returns an integer corresponding the to index number of the candidate that has won the coin toss.
     */
    private int coinToss(int[] tieFolk) {
        Random random = new Random();
        int randomNumber = random.nextInt(tieFolk.length);
        return tieFolk[randomNumber];
    }

    /**
     * Finds the index number of the candidate with the fewest number of votes. If their is a tie
     * between two or more candidates with the fewest number of votes, randomly choose one candidate
     * from the tied candidates to be the candidate corresponding to the fewest number of votes.
     * Writes information to the audit file, writing the name of the candidate(s) with the fewest number
     * of votes.
     * Used as a helper function for run().
     * @return an int corresponding to the index number of the candidate with the fewest number of votes
     * @throws IOException if a write fails
     */
    private int findLowestCandidate() throws IOException {
        // find the index of the first candidate still in the running
        int remove = 0;
        while (candidates[remove] == null && remove < numCandidates) {
            remove++;
        }

        // initalize the lowest vote count to the vote count of the
        // first candidate still in the running
        int lowestVote = candidates[remove].getBallotCount();

        // number of candidates with the lowest votes
        int numberOfLowest = 1;

        // find the lowest Vote Count,
        for (int i = remove + 1; i < numCandidates; i++) {
            // check if candidates in running and if theie vote count 
            if (candidates[i] != null && candidates[i].getBallotCount() < lowestVote) {
                // set lowest vote to new lowest
                lowestVote = candidates[i].getBallotCount();
                // update the index of the candidate to be removed
                remove = i;
                // set the number of candidates with this lowest vote count to 1
                numberOfLowest = 1;
            } else if (candidates[i] != null && candidates[i].getBallotCount() == lowestVote) {
                // another candidate has tied with lowest vote count
                // increase the number of candidates who have that same lowest vote count
                numberOfLowest++;
            }
        }

        // if only one candidate has the lowest number of votes
        // remove the candidate at that index
        if (numberOfLowest == 1) {
            return remove;
        }

        // a tie has occured with two or more candidates, find the indexes of all losers
        int[] tieFolk = new int[numberOfLowest];
        int tieIndex = 0;

        // loop over all candidates
        for (int i = 0; i < numCandidates; i++) {
            // if candidate exists and has the lowest vote count, add their
            // index to the tieFolk array
            if (candidates[i] != null && candidates[i].getBallotCount() == lowestVote) {
                tieFolk[tieIndex] = i;
                tieIndex++;
            }
        }
        
        // write to audit, the names of all candidates who tied with
        // lowest vote count
        audit.writeTiedLoserCandidates(candidates, tieFolk);

        // call tieFolk to pick out a loser
        return coinToss(tieFolk);
    }

    /**
     * Removes the candidate with the lowest number of votes from the running. All ballots
     * that were originally delegated the the candidate are returned to IR_Election, and
     * the ballots are updated to point to their next top ranked vote. The number of
     * remaining candidates decreases by 1.
     * @param lowest is an integer that corresponds to the index of the candidate
     * with the lowest number of votes
     * 
     * No error handling if lowest is out of range or if candidate at lowest is
     * null. Seg faults upon error.
     * 
     * Is a helper function for run
     */
    private void removeLowestCandidate(int lowest) {
        // increment the ranks of all ballots that belong
        // to the candidate at index "lowest"
        
        // for (int i = 0; i < candidates[lowest].getBallotCount(); i++) {
        //     int curRank = candidates[lowest].getBallots()[i].getRank();
        //     candidates[lowest].getBallots()[i].setRank(curRank + 1);
        // }

        // give candidate's ballots back to IR_Election
        currentBallots = candidates[lowest].getBallots();
        currentBallotCount = candidates[lowest].getBallotCount();

        // remove candidate from the running
        candidates[lowest] = null;
        numRemainingCandidates -= 1;

        return;
    }

    /**
     * Checks if a majority or a popularity with fewer than two candidates has occured. If a majority
     * or a popularity with fewer than two candidates has occured, the index number of the winning
     * candidate is returned. If no majority or popularity has occured, -1 is returned.
     * Writes 
     * @return an integer corrresponding to the winning candidate or -1 if no candidate has won.
     * @throws IOException if a write fails in runPopularity().
     * 
     * Is a helper function for run()
     */
    private int checkMajority() throws IOException {
        // check if a candidate has a majority, over half the total number of ballots
        // return that candidates index number if there is a majority
        for (int i = 0; i < numCandidates; i++) {
            if (candidates[i] != null && candidates[i].getBallotCount() > numBallots/2) {
                return i;
            }
        }

        // if there is no majority and there are 2 or fewer candidates
        // run a popularity vote and return the results of popularity
        if (numRemainingCandidates <= 2) {
            return runPopularity();
        }

        // return -1 if no majority or popularity winner
        return -1;
    }

    /**
     * Checks if a candidate has won by popularity of votes. Assumes there are only two candidates
     * in the running. If a tie occurs with the highest number of votes, randomly chooses
     * a candidate to win through a coin toss and returns the winner. Writes to audit
     * if a tie occurs.
     * @return an int corresponding to the index number of the winning candidate
     * @throws IOException
     * 
     * A helper function for checkMajority()
     */
    private int runPopularity() throws IOException {
        // find the index of the first candidate still in the running
        int winner = 0;
        while (candidates[winner] == null) {
            winner++;
        }

        // initalize the highest vote count to the vote count of the
        // first candidate still in the running
        int highestVote = candidates[winner].getBallotCount();

        // loop over all candidates starting after first candidate still in the running
        for (int i = winner + 1; i < numCandidates; i++) {
            // check if candidate exists and its vote count
            if (candidates[i] != null && candidates[i].getBallotCount() > highestVote) {
                // set winner index to candidate with higher vote count
                return i;
            } else if (candidates[i] != null && candidates[i].getBallotCount() == highestVote) {
                // a tie has occured
                int[] tieFolk = {winner, i};
                //write that tie has occured in popularity with the names of the tied candidates
                audit.writeTiedWinnerCandidates(candidates, tieFolk);
                // return the winner of the coin toss
                return coinToss(tieFolk);
            }
        }

        // if other candidates have not tied or have a higher vote
        // return the candidate initally designated as the winner
        return winner;
    }


    /**
     * Shuffles the array of ballots contained in IR_Election.
     * Takes no parameters and returns nothing.
     * 
     * A helper funtion for run()
     */
    public void shuffleBallots() {
        Random rnd = ThreadLocalRandom.current();
        for (int i = currentBallots.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            IR_Ballot a = currentBallots[index];
            currentBallots[index] = currentBallots[i];
            currentBallots[i] = a;
        }
    }

    /**
     * Runs the IR algorithm and writes audit information to the audit file and displays the results
     * and winner information of the election to the screen.
     * Assumes the electionScanner begins on the second line, all information contained in the IR election
     * ballot file is entered properly, there is at least one ballot in the file and at least
     * one candidate. Takes no parameters and returns nothing. Generates an audit file.
     * 
     * Assumes election class has been initalized properly and so has audit object.
     * 
     * @throws PrinterException
     */
    public void run() throws IOException {
        // read the header infor form the file and write header to audit
        if (electionFile != null) {
            readIRHeader();
        } else {
            readIRHeaderMultiple();
        }

        this.audit.writeHeaderToFile(this.typeElection, this.numCandidates, this.candidates, this.numBallots);

        // read in all ballots and shuffle the ballots
        if (electionFile != null) {
            try {
                readIRBallots();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        } else {
            readIRBallotsMultiple();
        }
        
        // initialize IR_Table
        ir_table = new IR_Table(candidates, numBallots);
        
        shuffleBallots();

        // loop until election won
        while(true) {
            // delegate ballots to candidates, and write the current vote count and
            // ballots given to each candidate still in the running
            allocateBallots();
            ir_table.IR_Table_Round(candidates);
            audit.writeCandidatesBallots(candidates, numCandidates);

            // check if a candidate has won
            int winner = checkMajority();
            if (winner != -1) {
                // write winner to audit
                audit.writeWinner(candidates, winner);

                // display winner stats to screen
                System.out.println("Election Type: IR_Election");
                System.out.print("Candidate Winner: " + candidates[winner].getName());
                System.out.println(" " + candidates[winner].getParty());
                System.out.println("Total Ballots Cast: " + numBallots);
                double percentage = ((double)candidates[winner].getBallotCount()/numBallots) * 100;
                System.out.println("Votes Won: " + candidates[winner].getBallotCount());
                System.out.println("Percentage of Votes Won: " + String.format("%.2f",percentage));
                audit.close();
                try {
                    ir_table.IR_Table_Display();
                } catch (PrinterException e) {
                    e.printStackTrace();
                }
                return;
            }

            // find candidate with fewest votes if no-one has won and write to audit
            int lowest = findLowestCandidate();
            audit.writeLoser(candidates, lowest);

            // remove andidate with fewest votes and write to audit
            removeLowestCandidate(lowest);
            audit.writeBallotsReallocated(this.currentBallots, this.currentBallotCount);
        }
        
    }
    /** 
     * get the array of candidates contained in IR_eleciton 
     * @return Candidate[] a list of candidate objects that represent candidates
     */
    public Candidate[] getCandidates() {
        return this.candidates;
    } 
    /** 
     * set the array candidates in IR to candidates
     * @param candidates of type Candidate array used to set IR_Elections candidates
     */
    public void setCandidates(Candidate[] candidates) {
        this.candidates = candidates;
    }
    /** 
     * get the total number of candidates in an IR_Election
     * @return an int representing the number of candidates in an IR_Election
     */
    public int getNumCandidates() {
        return this.numCandidates;
    }
    /** 
     * set the total number of candidates in an IR_Election
     * @param numCandidates an int parameter used to set the total number of cnadidates
     */
    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }
    /** 
     * get the number of remaining candidates
     * @return int, the number of candidates still in the running
     */
    public int getNumRemainingCandidates() {
        return this.numRemainingCandidates;
    }
    /**
     * set the number of remaining candidates
     * @param numRemainingCandidates an int that represents the number of remaining candidates
     */
    public void setNumRemainingCandidates(int numRemainingCandidates) {
        this.numRemainingCandidates = numRemainingCandidates;
    }

    
    /** 
     * get array of current ballots
     * @return IR_Ballot[] representing a list of unallocated ballots
     */
    public IR_Ballot[] getCurrentBallots() {
        return this.currentBallots;
    }

    /**
     * set the array of current ballots
     * @param currentBallots an IR_Ballot[] that represents a list off unallocated ballots
     */
    public void setCurrentBallots(IR_Ballot[] currentBallots) {
        this.currentBallots = currentBallots;
    }

    /**
     * get the current count of ballots
     * @return an int representing the number of unallocated ballots
     */
    public int getCurrentBallotCount() {
        return this.currentBallotCount;
    }

    /**
     * set the current count of ballots
     * @param currentBallotCount an int representing the number of unallocated ballots
     */
    public void setCurrentBallotCount(int currentBallotCount) {
        this.currentBallotCount = currentBallotCount;
    }

    /**
     * get the audit that represents how the audit file is written into 
     * @return IR_Audit that represents how the audit file is written into
     */
    public IR_Audit getAudit() {
        return this.audit;
    }

    /**
     * set the audit that represents how the audit file is written into 
     * @param audit type IR_Audit that represents how the audit file is written into
     */
     
    public void setAudit(IR_Audit audit) {
        this.audit = audit;
    }


    /**
     * Reads header information from an IR_Election.csv file. Assumes the electionFile Scanner
     * is at the second line of a IR_Election ballot file. Updates an IR_elections list of candidates,
     * number of candidates, number of remaining candidates, current ballots, and current ballot count
     * information. Addidtally initalizes each candidates names, party, ballot count, and list of ballots.
     * Takes no parameters and returns nothing. Used as a helper function for run().
     */
    private void readIRHeaderMultiple() {
        // read second line of file and get the number of candidates
        // and move scanner to the next line with candidates afterwards
        Scanner electionFile = electionFiles[0];
        numCandidates = electionFile.nextInt();
        electionFile.nextLine();

        // number of candidates still in the running, set equal to total number of candidates
        numRemainingCandidates = numCandidates;

        // create an array of candidates
        candidates = new Candidate[numCandidates];

        // loop for the total number of candidates and fill in candidates fields for each candidate
        for (int i = 0; i < numCandidates; i++) {
            // get name and party of candidate
            String name = electionFile.next();
            String party = electionFile.next();

            //get party name in form of "(*)" where * is letter of the party and remove comma at end if present
            if (party.charAt(party.length() - 1) == ',') {
                party = party.substring(0, party.length() - 1);
            } else {
                party = party.substring(0, party.length());
            }
            
            //fill fields of candidates
            candidates[i] = new Candidate();
            candidates[i].setBallotCount(0);
            candidates[i].setBallots(null);
            candidates[i].setName(name);
            candidates[i].setParty(party);
        }
        electionFile.nextLine();

        // initialize numBallotsFile array
        numBallotsFile = new int[electionFiles.length];
        // get the total number of Ballots for first file
        numBallotsFile[0] = electionFile.nextInt();
        // scanner, now on lines with ballots
        electionFile.nextLine();

        numBallots = numBallotsFile[0];

        // iterate on all other Scanner objects to begin reading in ballots
        for (int i = 1; i < electionFiles.length; i++) {
            electionFiles[i].nextLine();
            electionFiles[i].nextLine();
            electionFiles[i].nextLine();
            numBallotsFile[i] = electionFiles[i].nextInt();
            electionFiles[i].nextLine();
            numBallots += numBallotsFile[i];
        }

        // set the current number of undistributed ballots to equal the total number of ballots
        currentBallotCount = numBallots;

        return;
    }
    /**
     * Reads ballot information from an IR_Election.csv file and initalizes corresponding fields.
     * Assumes electionScanner is at the first line correlating to a ballots form information.
     * Creates a series of ballot objects and initalizes each ballot object using a ballot's form information.
     * Updates the IR_Elections ballots list to include each ballot object and initalizes
     * each candidate to have an array of ballots that can hold the total number of ballots.
     * Takes no parameters and returns nothing. Used as a helper function for run().
     */
    private void readIRBallotsMultiple() {
        // create an array of ballots equal to the number of total ballots in the file
        IR_Ballot[] ballots = new IR_Ballot[numBallots];
        int index = 0;
        // loop over all IR_Election files
        for (int d = 0; d < electionFiles.length; d++) {
            
            // loop over all ballots in the file and create a ballot for each ballot and add to total list of undistributed ballots
            Scanner electionFile = electionFiles[d];
            for (int i = 0; i < numBallotsFile[d]; i++) {
                
                // ballot num goes from 0 to total - 1, for 6 ballots, numbered 0 to 5

                // create a new ballot object and initalize rank, ballot number, and form
                IR_Ballot ballot = new IR_Ballot();
                ballot.setRank(0);
                ballot.setBallotNum(index);
                ballot.setForm(electionFile.next());

                // create and initalize a candidate Ranking Array
                int[] candidateRanking = new int[numCandidates];

                // initalize the rankings for each candidate to be -1
                for (int k = 0; k < numCandidates; k++) {
                    candidateRanking[k] = -1;
                }

                // using form, update candidate ranking array. Position correlates to candidate number
                // where position = 0, correlates to candidates[0]
                int position = 0;

                // loop over every character of the ballots form. example of form format: "1,,2,3"
                for (int j = 0; j < ballot.getForm().length(); j++) {
                    // get current character
                    char curChar = ballot.getForm().charAt(j);

                    // check if the character is a ranking or a comma
                    if (curChar == ',') {
                        // update position correlating to candidate number if a comma
                        position++;
                    } else {
                        // get the candidates ranking of the candidates corresponding position/index
                        String ranks = "";
                        ranks += curChar;
                        //add letters if number is double digits
                        if ((j + 1) < ballot.getForm().length()) {
                            while (((j + 1) < ballot.getForm().length()) && (ballot.getForm().charAt(j + 1) != ',')) {
                                j++;
                                curChar = ballot.getForm().charAt(j);
                                ranks += curChar;
                            }
                        }
                        int rank = Integer.parseInt(ranks);

                        // the candidates indexes are sorted in the candidate ranking array
                        // according to their rank, ex. a form "3,,1,2" would result in "{2,3,0, -1, -1}",
                        // candidate with index 2 is ranked first and candidate with index 0 is ranked third.
                        // Set candidates ranking at [rank -1] (since arrays start at 0), and set it equal
                        // to the candidates position in the candidates array
                        candidateRanking[rank - 1] = position;
                    }
                }

                // set ballots candidate ranking
                ballot.setCandidateRanking(candidateRanking);
                
                // add the newly made and initalized ballot to the list of ballots
                ballots[index] = ballot;
                index++;
            }
        }
       

        // update IR_Elections current unallocated ballots to equal the list of processed ballots
        // read in from the file
        currentBallots = ballots;

        // initalize an empty array of ballots for each candidate correlating
        // to the size of the total possible number ballots a candidate can have
        for (int i = 0; i < numCandidates; i++) {
            IR_Ballot [] ballotbox = new IR_Ballot[numBallots];
            candidates[i].setBallots(ballotbox);
        }

        return;
    }
    
    /**
     * Displays IR Election results table
     * 
     * @throws PrinterException
     */
    public void IR_Election_Table() throws PrinterException {
        JTable ir_election = new JTable();
        ir_election.print();
    }
}