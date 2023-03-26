package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;


public class CPL_Election extends Election {

    private int numParties;                 
    private int numSeats;
    private int availableSeats;
    private int numBallots;

    private CPL_Audit auditer;

    private CPL_Ballot[] initialBallots;
    private Party[] parties;


    public CPL_Election(Scanner electionFile, String date) throws IOException {
        super(electionFile);
        auditer = new CPL_Audit(date);      
        this.readCPLHeader();
        this.readCPLBallots();
        
    }
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
    private Party coinToss(ArrayList<Party> tiedParties) {
        Random random = new Random();
        int randomNumber = random.nextInt(tiedParties.size());
        Party temp = tiedParties.get(randomNumber);
        tiedParties.remove(randomNumber);
        return temp;
    }
    private void displayWinners() {
        System.out.println("CPL Election Results:");
        for (int i = 0; i < numParties; i++) {
            System.out.println(parties[i].getPartyName() + " : ");
            System.out.println("Number of votes cast: " + parties[i].getInitialBallotCount());
            System.out.printf("Vote percentage: %.2f\n", (double)parties[i].getInitialBallotCount()/numBallots);
            System.out.println("Number of seats won: " + parties[i].getSeatsWon());
            System.out.print("Winning Candidates: ");
            for (int j = 0; j < parties[i].getSeatsWon(); j++) {
                System.out.print(parties[i].getCandidateList().get(j));
                if (j != parties[j].getSeatsWon() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
    private boolean setFirstSeatsAllocation() throws IOException {

        for (int i = 0; i < initialBallots.length; i++) {   // increment ballot count for each party
            int index = initialBallots[i].getPartyNum();
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
    private void setRemainderSeatsAllocation() throws IOException {

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
    private void shuffleBallots() {
        Random rnd = ThreadLocalRandom.current();
        for (int i = initialBallots.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            CPL_Ballot a = initialBallots[index];            // swap ballots
            initialBallots[index] = initialBallots[i];
            initialBallots[i] = a;
        }
    }
    private void readCPLHeader() throws IOException {
        
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
        this.setNumBallots(electionFile.nextInt());         // get number of ballots
        electionFile.nextLine();

        auditer.writeHeaderToFile("CPL", parties, numBallots, numSeats);

    }
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
    private void readCPLBallots() {

        String ballot;
        initialBallots = new CPL_Ballot[this.getNumBallots()];
        for (int i = 0; i < this.getNumBallots(); i++) {     // create ballots
            ballot = electionFile.nextLine();
            int partyNum = ballot.indexOf("1");              // partyNum is index into parties[]
            CPL_Ballot temp = new CPL_Ballot(partyNum, i);
            initialBallots[i] = temp;                        // store ballot in system
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
        this.availableSeats = numSeats;
    }
    public int getNumBallots() {
        return numBallots;
    }
    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }
}
