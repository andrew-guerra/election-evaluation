public class Party {
    private String partyName;
    private String candidateList;
    private int numCandidate;
    private int seatsWon;
    private int ballotCount;
    private CPL_Ballot[] ballots;
    private int remainder;

    public Party(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyName() {
        return this.partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getCandidateList() {
        return this.candidateList;
    }

    public void setCandidateList(String candidateList) {
        this.candidateList = candidateList;
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

    public void setSeatsWon(int seatsWon) {
        this.seatsWon = seatsWon;
    }

    public int getBallotCount() {
        return this.ballotCount;
    }

    public void setBallotCount(int ballotCount) {
        this.ballotCount = ballotCount;
    }

    public CPL_Ballot[] getBallots() {
        return this.ballots;
    }

    public void setBallots(CPL_Ballot[] ballots) {
        this.ballots = ballots;
    }

    public int getRemainder() {
        return this.remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }
    
}