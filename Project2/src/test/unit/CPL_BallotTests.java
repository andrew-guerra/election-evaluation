package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.CPL_Ballot;

public class CPL_BallotTests {
    @Test
    public void testCPL_Ballot() {
        // test if ballot can be instantiated with 0

        CPL_Ballot ballot = new CPL_Ballot(0, 0);
        assertEquals(0, ballot.getPartyNum());
        assertEquals(0, ballot.getBallotNum());

        // test if ballot can be instantiated with integer
        int n1 = 1;
        int n2 = 2;
        ballot.setPartyNum(n1);
        ballot.setBallotNum(n2);
        assertEquals(1, ballot.getPartyNum());
        assertEquals(2, ballot.getBallotNum());
    }
}
