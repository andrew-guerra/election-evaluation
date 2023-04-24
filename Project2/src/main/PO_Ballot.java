package main;

/**
 * A class to represent a ballot in a PO election
 * 
 * @author  Andrew Guerra
 */
public class PO_Ballot {

    private int partyNum;
    private int ballotNum;

    /**
     * Creates a new PO_Ballot object with partyNum number of parties and ballotNum number of ballots
     * 
     * @param partyNum      number of parties
     * @param ballotNum     number of ballots
     */
    public PO_Ballot(int partyNum, int ballotNum) {
        this.partyNum = partyNum;
        this.ballotNum = ballotNum;
    }

    /**
     * Returns number of parties
     * 
     * @return      number of parties
     */
    public int getPartyNum() {
        return this.partyNum;
    }

    /**
     * Sets number of parties
     * 
     * @param partyNum
     */
    public void setPartyNum(int partyNum) {
        this.partyNum = partyNum;
    }

    /**
     * Get number of ballots
     * 
     * @return      number of ballots
     */
    public int getBallotNum() {
        return this.ballotNum;
    }

    /**
     * Set number of ballots
     * 
     * @param ballotNum     number of ballots
     */
    public void setBallotNum(int ballotNum) {
        this.ballotNum = ballotNum;
    }
}
