package Project1.src;

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

        boolean secondRound;
        this.shuffleBallots();                          // shuffle ballots
        secondRound = this.setFirstSeatsAllocation();   // allocate seats in first round of tallying
        if (secondRound) {
            this.setRemainderSeatsAllocation();         // allocate remaining seats
        }
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
            System.out.println(parties[i].getPartyName() + " - " + parties[i].getSeatsWon() + " seats won with " + parties[i].getInitialBallotCount() + " votes cast.");
        }
    }
    private boolean setFirstSeatsAllocation() {

        for (int i = 0; i < initialBallots.length; i++) {
            parties[initialBallots[i].getPartyNum()].incBallot();       // increment ballot count for party
        }
        // TODO: What if quota has a remainder?
        int quota = numBallots / numSeats;  // quota calculation
        int seatsWon;                       // seats won per party
        int seatsRemain;                    // seatsRemain if not enough candidates in party
        int ballotsRemain;                  // ballots that remain after seat allocation

        for (int i = 0; i < numParties; i++) {
            parties[i].setInitialBallotCount(parties[i].getBallotCount());
            seatsWon = parties[i].getBallotCount() / quota;
            
            ballotsRemain = parties[i].getBallotCount() % quota;
            if (seatsWon > parties[i].getNumCandidate()) {              // check if more seats allocated then candidates
                seatsRemain = seatsWon - parties[i].getNumCandidate();  // calculate number of seats that cannot be allocated
                parties[i].setRemainder(0);
                parties[i].incSeatsWon(seatsWon - seatsRemain);
                availableSeats = availableSeats - (seatsWon - seatsRemain);
            } else {
                parties[i].incSeatsWon(seatsWon);
                availableSeats = availableSeats - seatsWon;
            }
            parties[i].setBallotCount(ballotsRemain);                   // set party with remaining ballots

        }
        if (availableSeats == 0) {                                      // check if second round is needed
            return false;
        } else {
            return true;
        }
        
        
    }
    private void setRemainderSeatsAllocation() {

        ArrayList<Party> tiedParties = new ArrayList<Party>();
        int quota = numBallots / numSeats;
        int largestRemainder = 0;
        int partyToAllocate;
        while (availableSeats > 0) {
            largestRemainder = 0;
            for (int i = 0; i < numParties; i++) {
                //if (parties[i].getBallotCount() != 0) {                     // find party(s) with largest remainder
                    System.out.println(parties[i].getBallotCount());
                    int tempRemainder = parties[i].getBallotCount();
                    if (tempRemainder > largestRemainder) {
                        tiedParties.clear();
                        tiedParties.add(parties[i]);
                        largestRemainder = tempRemainder;
                        partyToAllocate = i;
                    } else if (tempRemainder == largestRemainder) {
                        tiedParties.add(parties[i]);
                    }
                //}
            }
            if (largestRemainder == 0) {                                    // check if seats left after all seats allocated
                while (availableSeats > 0) {
                    Party temp = this.coinToss(tiedParties);                // coin toss returns party removed from list that wins a seat
                    if (temp.getSeatsWon() < temp.getNumCandidate()) {
                        temp.incSeatsWon(1);
                        availableSeats--;
                    } 
                }
                break;
            }
            while(tiedParties.size() > 0) {
                if (availableSeats == 0) {                                  // all seats allocated
                    break;
                }
                if (availableSeats < tiedParties.size()) {                  // more tied parties than available seats
                    Party temp = this.coinToss(tiedParties);
                    if (temp.getSeatsWon() < temp.getNumCandidate()) {
                        temp.incSeatsWon(1);
                        temp.setBallotCount(0);
                        availableSeats--;
                    } else {
                        temp.setBallotCount(0);
                    }
                }
                if (availableSeats >= tiedParties.size()) {                 // check if there are enough seats for remainder allocation
                    for (int i = 0; i < tiedParties.size(); i++) {
                        if (tiedParties.get(i).getSeatsWon() < tiedParties.get(i).getNumCandidate()) {
                            tiedParties.get(i).incSeatsWon(1);
                            tiedParties.get(i).setBallotCount(0);
                            tiedParties.remove(i);           
                            availableSeats--;
                        } else {
                            System.out.println(tiedParties.get(i).getBallotCount());
                            tiedParties.get(i).setBallotCount(0);
                            tiedParties.remove(i);
                            
                        }
                        
                    }
                }
            }
        }
        

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
            parties[i].setNumCandidate(getNumberCandidates(candidateList));
        }
        this.setNumSeats(electionFile.nextInt());       // get number of seats
        electionFile.nextLine();
        this.setNumBallots(electionFile.nextInt());     // get number of ballots
        electionFile.nextLine();

    }
    private int getNumberCandidates(String candidates) {
        int counter = 0;
        if (candidates == "") {
            return 0;
        }
        while (true) {
            if (candidates.indexOf(",") == -1) {
                break;
            } else {
                counter++;
                candidates = candidates.substring(candidates.indexOf(",") + 1);
            }
            
        }
        return counter + 1;
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
        this.availableSeats = numSeats;
    }
    public int getNumBallots() {
        return numBallots;
    }
    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }
}