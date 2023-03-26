package main;

import java.util.ArrayList;

public class Party {
    private String partyName;
    private ArrayList<String> candidates;
    private int numCandidate;
    private int seatsWon;
    private int ballotCount;
    private int initialBallotCount;
    private ArrayList<CPL_Ballot> ballots;
    private int remainder;

    public Party(String partyName) {
        this.partyName = partyName;
        ballots = new ArrayList<CPL_Ballot>();
        ballotCount = 0;
    
    }

    public String getPartyName() {
        return this.partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public ArrayList<String> getCandidateList() {
        return this.candidates;
    }

    public void setCandidateList(ArrayList<String> candidates) {
        this.candidates = candidates;
    }

    public int getNumCandidate() {
        return this.numCandidate;
    }

    public void setNumCandidate(int numCandidate) {
        this.numCandidate = numCandidate;
    }

    public int getSeatsWon() {
        return this.seatsWon;
    }

    public void incSeatsWon(int seatsWon) {
        this.seatsWon = this.seatsWon + seatsWon;
    }

    public int getBallotCount() {
        return this.ballotCount;
    }

    public void setBallotCount(int ballotCount) {
        this.ballotCount = ballotCount;
    }
    public int getInitialBallotCount() {
        return this.initialBallotCount;
    }

    public void setInitialBallotCount(int initialBallotCount) {
        this.initialBallotCount = initialBallotCount;
    }

    public ArrayList<CPL_Ballot> getBallots() {
        return this.ballots;
    }

    public void addBallot(CPL_Ballot ballot) {
        ballots.add(ballot);
    }

    public int getRemainder() {
        return this.remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }
    public void incBallot() {
        ballotCount = ballotCount + 1;
    }
    
}
