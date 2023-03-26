package main;


/**
 * A class that represents a ballot includes it's ID,
 * current rank, form, and candidate rankings
 * 
 * @Author Hady Kotifani
 */
public class IR_Ballot {
    
    private int ballotNum; 
    private int rank; 
    private String form; 
    private int[] candidateRanking; 

    /**
     * default constructor for IR_Ballot, setting its fields to 0, empty strings, and null
     */
    public IR_Ballot() {
        ballotNum = 0;
        rank = 0;
        form = "";
        candidateRanking = null;
    }

    /**
     * constructor for IR_Ballot
     * @param ballotNum int, ID of ballot
     * @param rank int, current rank of ballot
     * @param form String, ballot rankings as a string with commas
     * @param candidateRanking int[], holds the candidates index at its respective rank in the array
     * and is -1 if not ranked
     */
    public IR_Ballot(int ballotNum, int rank, String form, int[] candidateRanking) {
        this.ballotNum = ballotNum;
        this.rank = rank;
        this.form = form;
        this.candidateRanking = candidateRanking;
    }

    /**
     * get the ballots ID
     * @return int, the ballots ID
     */
    public int getBallotNum() {
        return this.ballotNum;
    }

    /**
     * set the ballots ID
     * @param ballotNum int, the ballots ID
     */
    public void setBallotNum(int ballotNum) {
        this.ballotNum = ballotNum;
    }

    /**
     * get current rank of ballot
     * @return int, rank of ballot
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * set the rank of the ballot
     * @param rank int, rank of the ballot
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * get the form of the ballot
     * @return String, the form of the ballot
     */
    public String getForm() {
        return this.form;
    }

    /**
     * set the form of the ballot
     * @param form String, form of the ballot
     */
    public void setForm(String form) {
        this.form = form;
    }

    /**
     * get the rankings of the candidates
     * @return int[], the ranking of the candidates
     */
    public int[] getCandidateRanking() {
        return this.candidateRanking;
    }

    /**
     * set the candidates ranking
     * @param candidateRanking int[], the ranking of the candidates
     */
    public void setCandidateRanking(int[] candidateRanking) {
        this.candidateRanking = candidateRanking;
    }

    /**
     * get Candidates intex that is at some rank i.
     * error if the rank is out of bounds and returns -1.
     * @param i int, current rank of ballot
     * @return int, the index of the candidate at the ranking
     */
    public int getCandidateAtNum(int i) {
        if (i >= candidateRanking.length) {
            return -1;
        } else {
            return candidateRanking[i];
        }
    }

}