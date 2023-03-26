package test.unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.Candidate;
import main.IR_Ballot;;

public class IR_CandidateTests {
    @Test
    public void all_Candidate() {
        //test deflt and getters
        Candidate deflt = new Candidate();
        assertEquals(0, deflt.getBallotCount());
        assertArrayEquals(null, deflt.getBallots());
        assertEquals("", deflt.getName());
        assertEquals("", deflt.getParty());


        // test other constructors
        int[] ranking = {1,2,3};
        IR_Ballot ballot1 = new IR_Ballot(0, 0, "1,1,1,", ranking);
        IR_Ballot ballot2 = new IR_Ballot(0, 0, "1,1,1,", ranking);
        IR_Ballot ballot3 = new IR_Ballot(0, 0, "1,1,1,", ranking);

        IR_Ballot[] ballots = {ballot1, ballot2, ballot3};

        deflt = new Candidate(3, ballots, "Name", "Party");
        assertEquals(3, deflt.getBallotCount());
        assertArrayEquals(ballots, deflt.getBallots());
        assertEquals("Name", deflt.getName());
        assertEquals("Party", deflt.getParty());

        // test setters
        ranking[0] = 2;
        IR_Ballot ballot4 = new IR_Ballot(5, 6, "2,1,4", ranking);
        ballots[0] = ballot4;
        ballots[2] = null;

        deflt.setBallotCount(2);
        deflt.setBallots(ballots);
        deflt.setName("Tyler");
        deflt.setParty("(R)");

        assertEquals(2, deflt.getBallotCount());
        assertArrayEquals(ballots, deflt.getBallots());
        assertEquals("Tyler", deflt.getName());
        assertEquals("(R)", deflt.getParty());

        // test add, ballot count is 2, 3rd ballot is null, should be able to add ballot 4
        deflt.addBallot(ballot4);
        ballots[2] = ballot4;
        assertArrayEquals(ballots, deflt.getBallots());

        // check that array doesn't change or crash
        deflt.addBallot(ballot4);
        deflt.addBallot(ballot4);
        deflt.addBallot(ballot4);
        deflt.addBallot(ballot4);
        deflt.addBallot(ballot4);
        assertArrayEquals(ballots, deflt.getBallots());
        
    }
}
