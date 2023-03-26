package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.CPL_Ballot;

public class CPL_BallotTests {
    @Test
    public void testCPL_Ballot() {
        CPL_Ballot ballot = new CPL_Ballot(0, 0);
    
        assertEquals(0, ballot.getPartyNum());
        assertEquals(0, ballot.getBallotNum());

        ballot.setPartyNum(1);
        ballot.setBallotNum(2);

        assertEquals(1, ballot.getPartyNum());
        assertEquals(2, ballot.getBallotNum());
    }
}
