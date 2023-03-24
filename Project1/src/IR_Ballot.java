//package Project1.src;

// imports if needed
// added toString
// added constructor with more fields

public class IR_Ballot {
    private int ballotNum; 
    private int rank; 
    private String form; 
    private int[] candidateRanking; 

    // constructors
    public IR_Ballot() {
        ballotNum = 0;
        rank = 0;
     // form = "";
        candidateRanking = null;
    }

    public IR_Ballot(int ballotNum, int rank, String form, int[] candidateRanking) {
        this.ballotNum = ballotNum;
        this.rank = rank;
        this.form = form;
        this.candidateRanking = candidateRanking;
    }

    // all getters and setters
    public int getBallotNum() {
        return this.ballotNum;
    }

    public void setBallotNum(int ballotNum) {
        this.ballotNum = ballotNum;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int[] getCandidateRanking() {
        return this.candidateRanking;
    }

    public void setCandidateRanking(int[] candidateRanking) {
        this.candidateRanking = candidateRanking;
    }

    // toString method
    @Override
    public String toString() {
        return "{" +
            " ballotNum='" + getBallotNum() + "'" +
            ", rank='" + getRank() + "'" +
            ", form='" + getForm() + "'" +
            ", candidateRanking='" + getCandidateRanking() + "'" +
            "}";
    }


    //NEW BALLOTS NOT IN UML
    public int getCandidateAtNum(int i) {
        return candidateRanking[i];
    }

}