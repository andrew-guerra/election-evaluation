// add imports
// added toString
// added constructor with more fields

public class Candidate {

    private int ballotCount;
    private IR_Ballot [] ballots;
    private String name;
    private String party;

    //constructors
    public Candidate() {
        ballotCount = 0;
        ballots = null;
        name = "";
        party = "";
    }
    
    public Candidate(int ballotCount, IR_Ballot[] ballots, String name, String party) {
        this.ballotCount = ballotCount;
        this.ballots = ballots;
        this.name = name;
        this.party = party;
    }

    // all getters and setters
    public int getBallotCount() {
        return this.ballotCount;
    }

    public void setBallotCount(int ballotCount) {
        this.ballotCount = ballotCount;
    }

    public IR_Ballot[] getBallots() {
        return this.ballots;
    }

    public void setBallots(IR_Ballot[] ballots) {
        this.ballots = ballots;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return this.party;
    }

    public void setParty(String party) {
        this.party = party;
    }


    //METHODS NOT LISTED ON SDD
    // to string method
    @Override
    public String toString() {
        return "{" +
            " ballotCount='" + getBallotCount() + "'" +
            ", ballots='" + getBallots() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }

    //helper, add ballot
    public void addBallot(IR_Ballot ballot) {
        ballots[ballotCount] = ballot;
        ballotCount++;
    }
}
