
public class CPL_Ballot {

    private int partyNum;
    private int ballotNum;

    public CPL_Ballot(int partyNum, int ballotNum) {
        this.partyNum = partyNum;
        this.ballotNum = ballotNum;
    }

    public int getPartyNum() {
        return this.partyNum;
    }

    public void setPartyNum(int partyNum) {
        this.partyNum = partyNum;
    }

    public int getBallotNum() {
        return this.ballotNum;
    }

    public void setBallotNum(int ballotNum) {
        this.ballotNum = ballotNum;
    }

}