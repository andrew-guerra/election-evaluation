/** 
 * IR_Ballot is a class the handels the ballots for an IR Election
 * 
 * @author Tyler Grimm and 
 */

public class IR_Ballot { 
    private int ballotNum; 
    private int rank; 
    private char[] form; 
    private int[] candidateRanking; 

    public IR_Ballot(int ballotNum, int rank, char[] form, int[] candidateRanking){ 
        this.ballotNum = ballotNum;
        this.rank = rank;
        this.form = form; 
        this.candidateRanking = candidateRanking;
    }

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

    public char[] getForm() {
        return this.form;
    }

    public void setForm(char[] form) {
        this.form = form;
    }

    public int[] getCandidateRanking() {
        return this.candidateRanking;
    }

    public void setCandidateRanking(int[] candidateRanking) {
        this.candidateRanking = candidateRanking;
    }

}