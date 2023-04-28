package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;

/**
 * A class that handles functionality for the CPL_Election. It uses a Scanner of the electionFile
 * to read in the contents of the CPL_Election, including election info and all ballots. There are two rounds of seat allocation,
 * the first round is done by a quota, and the second round is done by a remainder of the ballots within a party. CPL_Election has
 * a CPL_Audit object that it writes to as the election progresses. Any method that writes to the CPL_Audit object throws an
 * IOException.
 * 
 * @author  Evan Bagwell
 */
public class CPL_Election extends Election {

    private int numParties;                 // attributes
    private int numSeats;
    private int availableSeats;
    private int numBallots;                 // ballots from all files
    private int[] numBallotsFile;           // ballots from a single file

    private CPL_Audit auditer;              // auditer writes the election to an audit file   

    private CPL_Ballot[] initialBallots;    // list of all ballots in CPL_Election
    private Party[] parties;                // list of all parties in CPL_Election

    /**
     * Instantiates a CPL_Election object. It is passed a Scanner object array of an election files to retrieve election data, and also 
     * a String represenation of the date of the election. The constructor instantiates a CPL_Audit object that is passed the date.
     * The constructor calls two private methods, readCPLHeader() and readCPLBallots() to set up the CPL_Election.
     * 
     * @param electionFiles
     * @param date
     * @throws IOException
     */
    public CPL_Election(Scanner[] electionFiles, String date) throws IOException {
        super(electionFiles);
        auditer = new CPL_Audit(date);      
        this.readCPLHeader();
        this.setFileScanners();
        this.readCPLBallots();
    }

    /**
     * Instantiates a CPL_Election object. It is passed a Scanner object of an election file to retrieve election data, and also 
     * a String represenation of the date of the election. The constructor instantiates a CPL_Audit object that is passed the date.
     * The constructor calls two private methods, readCPLHeader() and readCPLBallots() to set up the CPL_Election.
     * 
     * @param electionFile
     * @param date
     * @throws IOException
     */
    public CPL_Election(Scanner electionFile, String date) throws IOException {
        super(electionFile);
        auditer = new CPL_Audit(date);      
        this.readCPLHeader();
        this.setFileScanners();
        this.readCPLBallots();
    }
    
    /**
     * Helper method for run(). Reads header information from the first CPL_Election.csv file. Assumes the electionFile Scanner
     * is at the second line of a CPL_Election ballot file. Updates an CPL_elections list of parties along with their candidates,
     * number of parties, number of seats, current ballots, and current ballot count
     * information. Addidtally initalizes each Party and CPL_Ballot object. 
     * Takes no parameters and returns nothing. 
     * 
     * @throws IOException
     */
    private void readCPLHeader() throws IOException {
        if(electionFiles != null) {
            electionFile = electionFiles[0];        // read header info from first file
        }

        int numParties = electionFile.nextInt();            // get number of parties from header
        this.setNumParties(numParties);
        parties = new Party[numParties];
                    
        electionFile.nextLine();                            // set scanner to read party names
        String temp = electionFile.nextLine();
        
        for (int i = 0; i < numParties; i++) {              // create party objects
            String partyName;
            int index = temp.indexOf(",");
            if (index != -1) {                              // case where >1 parties remain
                partyName = temp.substring(0, index);   
                temp = temp.substring(index + 2);
            } else {                                        // case where one party remains
                partyName = temp;
            }
             
            Party partyAdd = new Party(partyName);          // add party to list
            parties[i] = partyAdd;
        }
  
        String candidateListString;                         // String to hold comma deliminated candidates

        for (int i = 0; i < numParties; i++) {              // create ArrayList of candidates for each party
            candidateListString = electionFile.nextLine();
            ArrayList<String> candidates = this.candidateArray(candidateListString);
            parties[i].setCandidateList(candidates);
            parties[i].setNumCandidate(candidates.size());
        }
        this.setNumSeats(electionFile.nextInt());           // get number of seats
        electionFile.nextLine();
        int numBallotsFirstFile = electionFile.nextInt();
        this.setNumBallots(numBallotsFirstFile);            // set total number of ballots
        electionFile.nextLine();

        if(electionFiles != null) {
            numBallotsFile = new int[electionFiles.length];     // initializes the numBallotsFile array 
            numBallotsFile[0] = numBallotsFirstFile;            // set total number of ballots for file  
        }
    } 

    /**
     * Helper method for readCPLHeader(). Takes in a single String that contains the candidates of the party. Parses
     * the String using a comma delimiter and adds each candidate to an ArrayList.
     * 
     * @param candidateListString   String containing list of candidates
     * @return                      returns ArrayList of String candidates
     */
    private ArrayList<String> candidateArray(String candidateListString) {

        ArrayList<String> candidates = new ArrayList<String>();    // list of candidates for party to hold
        if (candidateListString == "") {                           // return empty if no candidates
            return null;
        }
        while (true) {                                             // parse through string using comma delimiter
            if (candidateListString.indexOf(",") == -1) {
                break;
            } else {
                String temp = candidateListString.substring(0,candidateListString.indexOf(","));
                candidates.add(temp);
                candidateListString = candidateListString.substring(candidateListString.indexOf(",") + 2);
            }
        }
        candidates.add(candidateListString);
        return candidates;
    }

    /**
     * Helper method for readCPLBallots(). Sets the Scanner object of each file to the place to read in ballots. 
     * Initializes the numBallotsFile[] array. Assumes Scanner objects are pointing at first line of a CPL.csv file.
     * Writes to CPL_Audit the information regarding the header.
     * Takes no parameters and returns nothing
     * @throws IOException
     */
    private void setFileScanners() throws IOException {
        if(electionFiles != null) {
            for (int i = 0; i < electionFiles.length; i++) {     // iterate through Scanner objects for files
                Scanner electionFile = electionFiles[i];
                if (i != 0) {                                    // do not read in Scanner used in CPL_Header
                    electionFile.nextLine();
                    int numPartiesTemp = electionFile.nextInt();
                    electionFile.nextLine();
                    electionFile.nextLine();
                    for (int j = 0; j < numPartiesTemp; j++) {
                        electionFile.nextLine();
                                
                    }
                    electionFile.nextLine();
                    int fileBallots = electionFile.nextInt();
                    numBallots = numBallots + fileBallots;       // add to total number of ballots
                    numBallotsFile[i] = fileBallots;             // add to file total of ballots
                    electionFile.nextLine();                     // Scanner ready to read in ballots
                }
            }
        } 
        
        auditer.writeHeaderToFile("CPL", parties, numBallots, numSeats);
    }

    /**
     * Helper method for run(). Reads ballot information from a CPL_Election.csv file. Assumes readCPLHeader() has been
     * previously called and Scanner is ready to read in the first ballot in the file. Creates a CPL_Ballot for each ballot read
     * in and stores in an array of CPL_Ballots.
     * Takes no parameters and returns nothing.
     */
    private void readCPLBallots() {
        int index = 0;
        initialBallots = new CPL_Ballot[numBallots];
        if(electionFiles != null) {
            for (int i = 0; i < electionFiles.length; i++) {            // iterate through files
                Scanner electionFile = electionFiles[i];
                String ballot;
                
                for (int j = 0; j < numBallotsFile[i]; j++) {           // iterate through a file and create ballots
                    ballot = electionFile.nextLine();
                    int partyNum = ballot.indexOf("1");                 // partyNum is index into parties[]
                    CPL_Ballot temp = new CPL_Ballot(partyNum, index);
                    initialBallots[index] = temp;                       // store ballot in system
                    index++;
                }
            }
        } else {
            String ballot;
            for (int j = 0; j < numBallots; j++) {                      // iterate through a file and create ballots
                ballot = electionFile.nextLine();
                int partyNum = ballot.indexOf("1");                     // partyNum is index into parties[]
                CPL_Ballot temp = new CPL_Ballot(partyNum, index);
                initialBallots[index] = temp;                           // store ballot in system
                index++;
            }
        }
        
        
    }

    /** 
     * Runs the CPL algorithm and writes audit information to the audit file and displays the results and winner
     * information of the eleciton to the screen. Assumes the electionFile Scanner object begins on the second line, all
     * information contained in the CPL election ballot file is entered properly, there is at least one ballot in the file and 
     * at least one candidate. Takes no parameters and returns nothing. Generates an audit file.
     * 
     * Assumes election class has been initialized properly and so has audit object
     * 
     * @throws IOException
     */
    public void run() throws IOException {

        boolean secondRound;
        this.shuffleBallots();                          // shuffle ballots
        secondRound = this.setFirstSeatsAllocation();   // allocate seats in first round of tallying
        if (secondRound) {
            this.setRemainderSeatsAllocation();         // allocate remaining seats
        }
        auditer.writeTotalSeatDistribution(parties);
        auditer.writeElectionWinners(parties);
        auditer.close();
        this.displayWinners();
    }

    /**
     * Shuffles the array of ballots contained in CPL_Election.
     * Takes no parameters and returns nothing.
     */
    public void shuffleBallots() {
        Random rnd = ThreadLocalRandom.current();
        for (int i = initialBallots.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            CPL_Ballot a = initialBallots[index];            // swap ballots
            initialBallots[index] = initialBallots[i];
            initialBallots[i] = a;
        }
    }

    /**
     * Helper method for run(). For each ballot, it is added to its respective party for which a vote was casted.
     * The function then calculates the number of seats won by each party via a quota, taken from an integer division of numBallots/numSeats. 
     * Number of seats won by a party may be limited by how many candidates it has. Remainder ballots leftover from division set for each party.
     * 
     * @return  returns boolean value indicating whether setRemainderSeatsAllocation() needs to be called.
     * @throws IOException
     */
    public boolean setFirstSeatsAllocation() throws IOException {

        for (int i = 0; i < initialBallots.length; i++) {   // increment ballot count for each party
            int index = initialBallots[i].getPartyNum();
            System.out.println("PartyNum for ballot " + i + ": index");
            parties[index].incBallot();                         
            parties[index].addBallot(initialBallots[i]);    // add ballot to to respective party
        }
  
        int quota = numBallots / numSeats;  // quota calculation
        int seatsWon;                       // seats won per party
        int seatsRemain;                    // seatsRemain if not enough candidates in party
        int ballotsRemain;                  // ballots that remain after seat allocation

        for (int i = 0; i < numParties; i++) {                                 // first round seat distribution
            parties[i].setInitialBallotCount(parties[i].getBallotCount());
            seatsWon = parties[i].getBallotCount() / quota;                    // seats won
            ballotsRemain = parties[i].getBallotCount() % quota;               // votes outstanding
            if (seatsWon > parties[i].getNumCandidate()) {                     // check if more seats allocated then candidates
                seatsRemain = seatsWon - parties[i].getNumCandidate();         // calculate number of seats that cannot be allocated
                parties[i].setRemainder(0);
                parties[i].incSeatsWon(seatsWon - seatsRemain);
                availableSeats = availableSeats - (seatsWon - seatsRemain);    // decrement total seats able to be allocated 
                ballotsRemain = 0;
            } else {
                parties[i].incSeatsWon(seatsWon);
                availableSeats = availableSeats - seatsWon;                    // decrement total seats able to be allocated
                
                
            }
            parties[i].setBallotCount(ballotsRemain);   // ballots in party unaccounted after first round

        }
        auditer.writeInitialPartyVotes(parties, numBallots);
        auditer.writeFirstSeatsAllocation(parties);
        auditer.writeRemainingVotes(parties, availableSeats);

        if (availableSeats == 0) {                      // check if second round is needed
            return false;
        } else {
            return true;
        }
        
    }

    /**
     * Helper method for run(). While there are seats available to be allocated, the function will allocate an additional seat to a
     * party based on the size of the remaining ballots calculated from the quota. It does this through looping - for each iteration it generates a list of parties
     * that share the greatest remainder of ballots and adds a seat to each party if there are enough candidates and are enough seats
     * left to allocate. Exceptions occur where multiple parties when seats but not enough seats for all of them, seats remain to be allocated
     * but all remaining ballots have been accounted for, and not enough candidates left in a party to allocate another seat.
     * 
     * @throws IOException
     */
    public void setRemainderSeatsAllocation() throws IOException {

        ArrayList<Party> tiedParties = new ArrayList<Party>();              // list to hold iteration of parties with greatest remainder votes

        while (availableSeats > 0) {                                        // complete a cycle if seats still remaining
            
            tiedParties = this.findGreatestRemainderParty();                // find party(s) with largest remainder

            if (tiedParties.get(0).getBallotCount() == 0) {                 // check if all ballots have been accounted for
                while (availableSeats > 0) {                                // coin toss for all parties to win extra seat while seats still remaining
                    Party temp = this.coinToss(tiedParties);                // coin toss returns party removed from list that wins a seat
                    if (temp.getSeatsWon() < temp.getNumCandidate()) {      // allocated seat
                        temp.incSeatsWon(1);
                        auditer.writeRemainingSeatsAllocated(temp, 1);
                        availableSeats--;
                    } else {                                                // not enough candidates to allocate seat
                        auditer.writeRemainingSeatsAllocated(temp, 2);
                    }
                }
                break;
            }
            while(tiedParties.size() > 0) {                                 // loop that accounts for each party in list for potential seat allocation
                if (availableSeats == 0) {                                  // all seats allocated
                    break;
                }
                if (availableSeats < tiedParties.size()) {                  // more tied parties than available seats
                    Party temp = this.coinToss(tiedParties);                // coin toss returns party removed from list that wins a seat
                    if (temp.getSeatsWon() < temp.getNumCandidate()) {      // allocated seat
                        temp.incSeatsWon(1);
                        temp.setBallotCount(0);
                        availableSeats--;
                        auditer.writeRemainingSeatsAllocated(temp, 1);
                    } else {                                                // not enough candidates to allocate seat
                        temp.setBallotCount(0);
                        auditer.writeRemainingSeatsAllocated(temp, 2);
                    }
                }
                if (availableSeats >= tiedParties.size()) {                 // enough seats for the list of largest remainder parties

                    this.largestRemainderSeatAllocator(tiedParties);        // standard allocation of remainder seats for parties

                }
            }
        }
    }

    /** 
     * Helper method for setRemainderSeatsAllocation(). Determines the Party from the remainder list who won a coin toss.
     * Used as a helper function for setRemainderSeatsAllocation().
     * @param tiedParties   an ArrayList of Party objects that have the same remainder of ballots.
     * @return              returns a Party object that won a coin toss and removed from ArrayList of Party objects.
     */
    private Party coinToss(ArrayList<Party> tiedParties) {
        Random random = new Random();
        int randomNumber = random.nextInt(tiedParties.size());
        Party temp = tiedParties.get(randomNumber);
        tiedParties.remove(randomNumber);
        return temp;
    }

    /**
     * Helper method for setRemainderSeatsAllocation(). Iterates through list of parties and creates an ArrayList of parties
     * that share the largest remainder of ballots. Writes to the CPL_Audit object the ArrayList of parties.
     * 
     * @return  returns an ArrayList of Party objects that have the largest remainder of ballots from all parties.
     * @throws IOException
     */
    private ArrayList<Party> findGreatestRemainderParty() throws IOException {

        ArrayList<Party> tiedParties = new ArrayList<Party>();  // list of tied parties
        int largestRemainder = 0;                               // the greatest number of remaining ballots in list of parties

        for (int i = 0; i < numParties; i++) {                  // iterate through every party to find greatest remainder of ballots
            int tempRemainder = parties[i].getBallotCount();
            if (tempRemainder > largestRemainder) {
                tiedParties.clear();                            // reset list
                tiedParties.add(parties[i]);                    // add new party with greatest remainder of ballots   
                largestRemainder = tempRemainder;
            } else if (tempRemainder == largestRemainder) {     // add party to list of tie
                tiedParties.add(parties[i]);
            }     
        }
        if (largestRemainder != 0) {
            auditer.writeGreatestRemainderParty(tiedParties, largestRemainder, availableSeats);
        } else {
            auditer.extraSeats(availableSeats);
        }
        return tiedParties;
    }

    /**
     * Helper method for setRemainderSeatsAllocation(). Iterates through list of parties and allocates them each one additional
     * seat if a party has enough candidates
     * 
     * @param tiedParties   list of parties that share the largest remainder of ballots.
     * @throws IOException
     */
    private void largestRemainderSeatAllocator(ArrayList<Party> tiedParties) throws IOException {
        for (int i = 0; i < tiedParties.size(); i++) {                                      // loop that iterates through list of tied parties
            if (tiedParties.get(i).getSeatsWon() < tiedParties.get(i).getNumCandidate()) {  // allocated seat
                tiedParties.get(i).incSeatsWon(1);
                tiedParties.get(i).setBallotCount(0);
                auditer.writeRemainingSeatsAllocated(tiedParties.get(i), 0);
                tiedParties.remove(i);           
                availableSeats--;
            } else {                                                                        // not enough candidates to allocate seat
                tiedParties.get(i).setBallotCount(0);
                auditer.writeRemainingSeatsAllocated(tiedParties.get(i), 2);
                tiedParties.remove(i);
            }
        }
    }

    /**
     * Helper method for run(). Displays election results to the screen, including party(s) participating in the election,
     * the number of votes cast for each party, vote percentage, number of seats won, and winning candidates for each party.
     */
    private void displayWinners() {
        System.out.println("CPL Election Results:");
        for (int i = 0; i < numParties; i++) {                              // iteratres through list of parties
            System.out.println(parties[i].getPartyName() + " : ");
            System.out.println("Number of votes cast: " + parties[i].getInitialBallotCount());
            System.out.printf("Vote percentage: %.2f\n", (double)parties[i].getInitialBallotCount()/numBallots);
            System.out.println("Number of seats won: " + parties[i].getSeatsWon());
            System.out.print("Winning Candidates: ");
            for (int j = 0; j < parties[i].getSeatsWon(); j++) {            // iterates through list of candidates for party
                System.out.print(parties[i].getCandidateList().get(j));
                if (j != parties[j].getSeatsWon() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    /**
     * get the total number of parties in a CPL_Election
     * @return  int representing the number of parties in a CPL_Election
     */
    public int getNumParties() {
        return numParties;
    }

    /**
     * set the total number of parties in a CPL_Election
     * @param numParties    int representing the number of parties in a CPL_Election
     */
    public void setNumParties(int numParties) {
        this.numParties = numParties;
    }

    /**
     * get the total number of seats in a CPL_Election
     * @return  int representing the number of seats in a CPL_Election
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * set the total number of seats in a CPL_Election
     * @param numSeats    int representing the number of seats in a CPL_Election
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
        this.availableSeats = numSeats;
    }

    /**
     * get the total number of ballots in a CPL_Election
     * @return  int representing the number of ballots in a CPL_Election
     */
    public int getNumBallots() {
        return numBallots;
    }

    /**
     * set the total number of ballots in a CPL_Election
     * @param numBallots    int representing the number of ballots in a CPL_Election
     */
    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }
    /**
     * get the auditer in a CPL_Election
     * @return  auditer of a CPL_Election
     */
    public CPL_Audit getAuditObject() {
        return auditer;
    }
    /**
     * set the auditer in a CPL_Election
     * @param auditer    auditer in a CPL_Election
     */
    public void setAuditObject(CPL_Audit auditer) {
        this.auditer = auditer;
    }
    /**
     * get the parties[] in a CPL_Election
     * @return  parties[] of a CPL_Election
     */
    public Party[] getParties() {
        return parties;
    }
    /**
     * set the parties[] in a CPL_Election
     * @param parties    parties[] in a CPL_Election
     */
    public void setParties(Party[] parties) {
        this.parties = parties;
    }
     /**
     * get the ballots[] in a CPL_Election
     * @return  ballots[] of a CPL_Election
     */
    public CPL_Ballot[] getBallots() {
        return initialBallots;
    }
    /**
     * set the ballots[] in a CPL_Election
     * @param initialBallots    ballots[] in a CPL_Election
     */
    public void setBallots(CPL_Ballot[] initialBallots) {
        this.initialBallots = initialBallots;
    }
    
}
