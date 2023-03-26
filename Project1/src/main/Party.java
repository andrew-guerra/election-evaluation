package main;

import java.util.ArrayList;

/**
 * A class that represents a Party in a CPL election
 * 
 * @author Evan Bagwell
 */
public class Party {

    private String partyName;
    private ArrayList<String> candidates;
    private int numCandidate;
    private int seatsWon;
    private int ballotCount;
    private int initialBallotCount;
    private ArrayList<CPL_Ballot> ballots;
    private int remainder;

    /**
     * Creates a Party object with partyName party name
     * 
     * @param partyName     name of party
     */
    public Party(String partyName) {
        this.partyName = partyName;
        ballots = new ArrayList<CPL_Ballot>();
        ballotCount = 0;
    
    }

    /**
     * Returns the party name
     * 
     * @return      name of party
     */
    public String getPartyName() {
        return this.partyName;
    }

    /**
     * Sets the party name
     * 
     * @param partyName     name of party
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Returns list of candidates names for party
     * 
     * @return      ArrayList of String objects for each party candidate
     */
    public ArrayList<String> getCandidateList() {
        return this.candidates;
    }

    /**
     * Sets list of candidates names for party
     * 
     * @param candidates    ArrayList of String objects for each party candidate
     */
    public void setCandidateList(ArrayList<String> candidates) {
        this.candidates = candidates;
    }

    /**
     * Returns number of candidates for party
     * 
     * @return      number of candidates for party
     */
    public int getNumCandidate() {
        return this.numCandidate;
    }

    /**
     * Sets number of candidates for party
     * 
     * @param numCandidate      number of candidates for party
     */
    public void setNumCandidate(int numCandidate) {
        this.numCandidate = numCandidate;
    }

    /**
     * Returns number of seats won for party
     * 
     * @return      number of seats won for party
     */
    public int getSeatsWon() {
        return this.seatsWon;
    }

    /**
     * Incremeents total number of seats won for party by seatsWon amount
     * 
     * @param seatsWon      amount for total seats won to be incremented
     */
    public void incSeatsWon(int seatsWon) {
        this.seatsWon = this.seatsWon + seatsWon;
    }

    /**
     * Returns number of ballots for party
     * 
     * @return      number of ballots for party
     */
    public int getBallotCount() {
        return this.ballotCount;
    }

    /**
     * Sets number of ballots for party
     * 
     * @param ballotCount       number of ballots for party
     */
    public void setBallotCount(int ballotCount) {
        this.ballotCount = ballotCount;
    }

    /**
     * Returns initial ballot count for party
     * 
     * @return      initial ballot count for party
     */
    public int getInitialBallotCount() {
        return this.initialBallotCount;
    }

    /**
     * Sets initial ballot count for party
     * 
     * @param initialBallotCount        initial ballot count for party
     */
    public void setInitialBallotCount(int initialBallotCount) {
        this.initialBallotCount = initialBallotCount;
    }

    /**
     * Returns ArrayList of ballots for party
     * 
     * @return      ArrayList of ballots for party
     */
    public ArrayList<CPL_Ballot> getBallots() {
        return this.ballots;
    }

    /**
     * Adds ballot to list of party ballots
     * 
     * @param ballot        ballot to be added to party ballots
     */
    public void addBallot(CPL_Ballot ballot) {
        ballots.add(ballot);
    }

    /**
     * Returns remainder for party
     * 
     * @return      remainder for party
     */
    public int getRemainder() {
        return this.remainder;
    }

    /**
     * Sets remainder for party
     * 
     * @param remainder     remainder for party
     */
    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    /**
     * Increments party ballots by one
     */
    public void incBallot() {
        ballotCount = ballotCount + 1;
    }
}
