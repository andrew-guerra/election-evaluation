package test.unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.IR_Ballot;;

public class IR_BallotTests {
    @Test
    public void testIR_Ballot() {
        char[] form = {};
        int[] candidateRanking = {};
        IR_Ballot ballot = new IR_Ballot(0, 0, form, candidateRanking);
        assertEquals(0, ballot.getBallotNum());
        assertEquals(0, ballot.getRank());
        assertArrayEquals(form, ballot.getForm());
        assertArrayEquals(candidateRanking, ballot.getCandidateRanking());

        ballot.setBallotNum(1);
        ballot.setRank(1);
        ballot.setForm(new char[]{'a', 'b', 'c'});
        ballot.setCandidateRanking(new int[]{1, 2, 3});
        assertEquals(1, ballot.getBallotNum());
        assertEquals(1, ballot.getRank());
        assertArrayEquals(new char[]{'a', 'b', 'c'}, ballot.getForm());
        assertArrayEquals(new int[]{1, 2, 3}, ballot.getCandidateRanking());
    }
}
