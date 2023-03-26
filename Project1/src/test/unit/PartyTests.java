package test.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import main.Party;

public class PartyTests {
    @Test
    public void testParty() {
        Party party = new Party("Gopher");
        assertEquals("Gopher", party.getPartyName());
        assertEquals(0, party.getBallotCount());
        assertEquals(0, party.getSeatsWon());

        party.setPartyName("Badger");
        party.setCandidateList(new ArrayList<String>(Arrays.asList("Bucky")));
        party.setNumCandidate(1);
        party.incSeatsWon(1);
        party.setInitialBallotCount(0);
        party.setBallotCount(1);

        assertEquals("Badger", party.getPartyName());
        assertEquals(1, party.getBallotCount());
        assertEquals(1, party.getSeatsWon());
        assertEquals(1, party.getNumCandidate());
        assertEquals(0, party.getInitialBallotCount());
    }
}
