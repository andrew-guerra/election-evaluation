package main;

/**
 * A class that represents a Candidate, their respective ballot counts,
 * ballots delinated for them, their name, and their party. Contains methods to add ballots
 * and set features of the candidate
 * 
 * @Author Hady Kotifani
 */
public class Candidate {

    private int ballotCount;
    private IR_Ballot [] ballots;
    private String name;
    private String party;

    /**
     * default constructor, initalizes count to zero, ballots to null, and empty strings
     */
    public Candidate() {
        ballotCount = 0;
        ballots = null;
        name = "";
        party = "";
    }
    
    /**
     * constructor for Candidate that sets attributes
     * @param ballotCount int, number of ballots held by candidate
     * @param ballots IR_Ballot[], list of ballots held by candidate
     * @param name, String, name of candidate
     * @param party String, party of candidate
     */
    public Candidate(int ballotCount, IR_Ballot[] ballots, String name, String party) {
        this.ballotCount = ballotCount;
        this.ballots = ballots;
        this.name = name;
        this.party = party;
    }

    
    /**
     * adds ballot to candidate's array of ballots and increase their ballot count.
     * fails to add ballot if ballots array is to small and prints error message.
     * @param ballot IR_Ballot, ballot being added to candidate
     */
    public void addBallot(IR_Ballot ballot) {
        // increment number of ballots a candidate has and adds it
        // to a candidates array of ballots
        if (ballotCount >= ballots.length) {
            System.out.println("cannot add ballot");
        } else {
            ballots[ballotCount] = ballot;
            ballotCount++;
        }
    }

    /**
     * get ballot count
     * @return int, ballot count
     */
    public int getBallotCount() {
        return this.ballotCount;
    }

    /**
     * set ballot count
     * @param ballotCount int, ballot count
     */
    public void setBallotCount(int ballotCount) {
        this.ballotCount = ballotCount;
    }

    /**
     * get array of ballots
     * @return IR_Ballot[], array of ballots
     */
    public IR_Ballot[] getBallots() {
        return this.ballots;
    }

    /**
     * set array of ballots
     * @param ballots IR_Ballot[], array of ballots
     */
    public void setBallots(IR_Ballot[] ballots) {
        this.ballots = ballots;
    }

    /**
     * get name of candidate
     * @return String, name of candidate
     */
    public String getName() {
        return this.name;
    }

    /**
     * set name of candidate
     * @param name String, name of candidate
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get party of candidate
     * @return String, party of candidate
     */
    public String getParty() {
        return this.party;
    }

    /**
     * set party of candidate
     * @param party String, party of candidate
     */
    public void setParty(String party) {
        this.party = party;
    }

    /**
     * increments ballot count by 1
     */
    public void incrementBallotCount() {
        this.ballotCount++;
    }
}
