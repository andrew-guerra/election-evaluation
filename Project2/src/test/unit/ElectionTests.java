package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.Election;

public class ElectionTests {
    @Test
    public void testElection() {
        Election election = new Election(0, "IR", "IR-election.csv", null);
        assertEquals(0, election.getNumBallots());
        assertEquals("IR", election.getTypeElection());
        assertEquals("IR-election.csv", election.getFileName());
        assertEquals(null, election.getElectionFile());

        election.setNumBallots(1);
        election.setTypeElection("CPL");
        election.setFileName("CPL-election.csv");
        election.setElectionFile(null);

        assertEquals(1, election.getNumBallots());
        assertEquals("CPL", election.getTypeElection());
        assertEquals("CPL-election.csv", election.getFileName());
        assertEquals(null, election.getElectionFile());
    }
}
