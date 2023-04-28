package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.PO_Ballot;

public class PO_BallotTests {
    @Test
    public void testConstructor() {
        PO_Ballot actualBallot = new PO_Ballot(0, 0);
        assertEquals(0, actualBallot.getPartyNum());
        assertEquals(0, actualBallot.getBallotNum());

        actualBallot = new PO_Ballot(1, 5);
        assertEquals(1, actualBallot.getPartyNum());
        assertEquals(5, actualBallot.getBallotNum());
    }

    @Test
    public void testGetPartyNum() {
        PO_Ballot actualBallot = new PO_Ballot(0, 0);
        assertEquals(0, actualBallot.getPartyNum());

        actualBallot = new PO_Ballot(1, 5);
        assertEquals(1, actualBallot.getPartyNum());
    }

    @Test
    public void testSetPartyNum() {
        PO_Ballot actualBallot = new PO_Ballot(0, 0);
        assertEquals(0, actualBallot.getPartyNum());

        actualBallot.setPartyNum(1);
        assertEquals(1, actualBallot.getPartyNum());

        actualBallot = new PO_Ballot(1, 0);
        assertEquals(1, actualBallot.getPartyNum());

        actualBallot.setPartyNum(5);
        assertEquals(5, actualBallot.getPartyNum());
    }

    @Test
    public void testGetBallotNum() {
        PO_Ballot actualBallot = new PO_Ballot(0, 0);
        assertEquals(0, actualBallot.getBallotNum());

        actualBallot = new PO_Ballot(1, 5);
        assertEquals(5, actualBallot.getBallotNum());
    }

    @Test
    public void testSetBallotNum() {
        PO_Ballot actualBallot = new PO_Ballot(0, 0);
        assertEquals(0, actualBallot.getBallotNum());

        actualBallot.setBallotNum(1);
        assertEquals(1, actualBallot.getBallotNum());

        actualBallot = new PO_Ballot(0, 1);
        assertEquals(1, actualBallot.getBallotNum());

        actualBallot.setBallotNum(5);
        assertEquals(5, actualBallot.getBallotNum());
    }
}
