package Project1.src;

public class Party {
    private String partyName;
    private String candidateList;
    private int numCandidate;
    private int seatsWon;
    private int ballotCount;
    private int initialBallotCount;
    private CPL_Ballot[] ballots;
    private int remainder;

    public Party(String partyName) {
        this.partyName = partyName;
        ballotCount = 0;
    
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
    public void incBallot() {
        ballotCount = ballotCount + 1;
    }
    
}
