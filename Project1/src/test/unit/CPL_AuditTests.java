package test.unit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;

import main.CPL_Election;
import main.CPL_Audit;
import main.Party;
import main.CPL_Ballot;

public class CPL_AuditTests {
    // automatic
    @Test
    public void testCPL_AuditConstructor() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-Constructor");
        assertEquals("Audit-Constructor", test.getDate());
        assertNotNull(test.getAuditFile());
        assertNotNull(test.getAuditWriter());
        test.close();
    }
    // manual
    @Test
    public void testCPL_AuditClose() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-Close");
        test.getAuditWriter().write("If you see this, close works");
        test.close();

    }   
    // automatic
    @Test
    public void testCPL_AuditGettersAndSetters() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-GettersAndSetters");
        test.setDate("00-00-0000");
        String filename = "CPL_testSetter";
        File auditFile = new File(filename); 
        test.setAuditFile(auditFile);
        FileWriter auditWriter;
        try {                                           
            auditWriter = new FileWriter(filename);
            test.setAuditWriter(auditWriter);
        } catch (IOException e) {
            System.out.println("auditWriter error");
            e.printStackTrace();
        }
        assertEquals("00-00-0000", test.getDate());
        assertEquals("CPL_testSetter", test.getAuditFile().getName());
        assertNotNull(test.getAuditWriter());
        test.close();
    }
    // manual
    @Test
    public void testCPL_AuditHeaderToFile() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-HeaderToFile");

        Party[] parties = new Party[1];
        Party testParty = new Party("testParty");
        testParty.addBallot(new CPL_Ballot(0, 0));
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add("testCandidate");
        testParty.setCandidateList(candidates);
        testParty.setNumCandidate(1);

        parties[0] = testParty;
        test.writeHeaderToFile("CPL", parties, 1, 1);
        test.close();
    }
    // manual
    @Test
    public void testCPL_AuditWriteInitialPartyVotes() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-WriteInitialPartyVotes");

        Party[] parties = new Party[2];

        Party testParty1 = new Party("testParty1");
        Party testParty2 = new Party("testParty2");

        testParty1.addBallot(new CPL_Ballot(0, 0));
        testParty2.addBallot(new CPL_Ballot(1, 1));

        ArrayList<String> candidates1 = new ArrayList<String>();
        ArrayList<String> candidates2 = new ArrayList<String>();

        candidates1.add("testCandidate1");
        candidates2.add("testCandidate2");

        testParty1.setCandidateList(candidates1);
        testParty1.setNumCandidate(1);
        testParty2.setCandidateList(candidates2);
        testParty2.setNumCandidate(1);

        parties[0] = testParty1;
        parties[1] = testParty2;

        testParty1.setInitialBallotCount(1);
        testParty2.setInitialBallotCount(1);

        test.writeInitialPartyVotes(parties, 2);
        test.close();
    }
    // manual
    @Test
    public void testCPL_AuditWriteFirstSeatAllocation() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-WriteFirstSeatAllocation");

        Party[] parties = new Party[2];

        Party testParty1 = new Party("testParty1");
        Party testParty2 = new Party("testParty2");

        testParty1.addBallot(new CPL_Ballot(0, 0));
        testParty2.addBallot(new CPL_Ballot(1, 1));

        ArrayList<String> candidates1 = new ArrayList<String>();
        ArrayList<String> candidates2 = new ArrayList<String>();

        candidates1.add("testCandidate1");
        candidates2.add("testCandidate2");

        testParty1.setCandidateList(candidates1);
        testParty1.setNumCandidate(1);
        testParty2.setCandidateList(candidates2);
        testParty2.setNumCandidate(1);

        parties[0] = testParty1;
        parties[1] = testParty2;

        testParty1.setInitialBallotCount(1);
        testParty2.setInitialBallotCount(1);
        testParty1.incSeatsWon(1);
        testParty2.incSeatsWon(1);

        test.writeFirstSeatsAllocation(parties);
        test.close();
    }
    // manual
    @Test
    public void testCPL_AuditWriteRemainingVotes() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-WriteRemainingVotes");

        Party[] parties = new Party[2];

        Party testParty1 = new Party("testParty1");
        Party testParty2 = new Party("testParty2");

        testParty1.addBallot(new CPL_Ballot(0, 0));
        testParty2.addBallot(new CPL_Ballot(1, 1));

        ArrayList<String> candidates1 = new ArrayList<String>();
        ArrayList<String> candidates2 = new ArrayList<String>();

        candidates1.add("testCandidate1");
        candidates2.add("testCandidate2");

        testParty1.setCandidateList(candidates1);
        testParty1.setNumCandidate(1);
        testParty2.setCandidateList(candidates2);
        testParty2.setNumCandidate(1);

        parties[0] = testParty1;
        parties[1] = testParty2;

        testParty1.setInitialBallotCount(1);
        testParty2.setInitialBallotCount(1);
        testParty1.incSeatsWon(1);
        testParty2.incSeatsWon(1);
        testParty1.setBallotCount(1);
        testParty2.setBallotCount(1);

        test.writeRemainingVotes(parties, 1);
        test.close();
    }
    // manual
    @Test
    public void testCPL_AuditWriteGreatestRemainderParty() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-WriteGreatestRemainingVotes");

        Party[] parties = new Party[2];

        Party testParty1 = new Party("testParty1");
        Party testParty2 = new Party("testParty2");

        testParty1.addBallot(new CPL_Ballot(0, 0));
        testParty2.addBallot(new CPL_Ballot(1, 1));

        ArrayList<String> candidates1 = new ArrayList<String>();
        ArrayList<String> candidates2 = new ArrayList<String>();

        candidates1.add("testCandidate1");
        candidates2.add("testCandidate2");

        testParty1.setCandidateList(candidates1);
        testParty1.setNumCandidate(1);
        testParty2.setCandidateList(candidates2);
        testParty2.setNumCandidate(1);

        parties[0] = testParty1;
        parties[1] = testParty2;
        ArrayList<Party> tiedParties = new ArrayList<Party>();
        tiedParties.add(testParty1);
        tiedParties.add(testParty2);

        testParty1.setInitialBallotCount(1);
        testParty2.setInitialBallotCount(1);
        testParty1.incSeatsWon(1);
        testParty2.incSeatsWon(1);
        testParty1.setBallotCount(1);
        testParty2.setBallotCount(1);

        test.writeGreatestRemainderParty(tiedParties, 1, 1);
        test.close();
    }
    // manual
    @Test
    public void testCPL_AuditWriteRemainingSeatsAllocated() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-WriteRemainingSeatsAllocated");

        Party[] parties = new Party[2];

        Party testParty1 = new Party("testParty1");
        Party testParty2 = new Party("testParty2");

        testParty1.addBallot(new CPL_Ballot(0, 0));
        testParty2.addBallot(new CPL_Ballot(1, 1));

        ArrayList<String> candidates1 = new ArrayList<String>();
        ArrayList<String> candidates2 = new ArrayList<String>();

        candidates1.add("testCandidate1");
        candidates2.add("testCandidate2");

        testParty1.setCandidateList(candidates1);
        testParty1.setNumCandidate(1);
        testParty2.setCandidateList(candidates2);
        testParty2.setNumCandidate(1);

        parties[0] = testParty1;
        parties[1] = testParty2;
        ArrayList<Party> tiedParties = new ArrayList<Party>();
        tiedParties.add(testParty1);
        tiedParties.add(testParty2);

        testParty1.setInitialBallotCount(1);
        testParty2.setInitialBallotCount(1);
        testParty1.incSeatsWon(1);
        testParty2.incSeatsWon(1);
        testParty1.setBallotCount(1);
        testParty2.setBallotCount(1);

        test.writeRemainingSeatsAllocated(testParty1, 0);
        test.close();
    }
    @Test
    public void testCPL_AuditExtraSeats() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-ExtraSeats");
        test.extraSeats(3);
        test.close();
    }
    @Test
    public void testCPL_AuditWriteTotalSeatDistribution() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-WriteTotalSeatDistribution");

        Party[] parties = new Party[2];

        Party testParty1 = new Party("testParty1");
        Party testParty2 = new Party("testParty2");

        testParty1.addBallot(new CPL_Ballot(0, 0));
        testParty2.addBallot(new CPL_Ballot(1, 1));

        ArrayList<String> candidates1 = new ArrayList<String>();
        ArrayList<String> candidates2 = new ArrayList<String>();

        candidates1.add("testCandidate1");
        candidates2.add("testCandidate2");

        testParty1.setCandidateList(candidates1);
        testParty1.setNumCandidate(1);
        testParty2.setCandidateList(candidates2);
        testParty2.setNumCandidate(1);

        parties[0] = testParty1;
        parties[1] = testParty2;
        ArrayList<Party> tiedParties = new ArrayList<Party>();
        tiedParties.add(testParty1);
        tiedParties.add(testParty2);

        testParty1.setInitialBallotCount(1);
        testParty2.setInitialBallotCount(1);
        testParty1.incSeatsWon(1);
        testParty2.incSeatsWon(1);
        testParty1.setBallotCount(1);
        testParty2.setBallotCount(1);

        test.writeTotalSeatDistribution(parties);
        test.close();
    }
    @Test
    public void testCPL_AuditWriteElectionWinners() throws IOException {

        CPL_Audit test = new CPL_Audit("Audit-WriteElectionWinners");

        Party[] parties = new Party[2];

        Party testParty1 = new Party("testParty1");
        Party testParty2 = new Party("testParty2");

        testParty1.addBallot(new CPL_Ballot(0, 0));
        testParty2.addBallot(new CPL_Ballot(1, 1));

        ArrayList<String> candidates1 = new ArrayList<String>();
        ArrayList<String> candidates2 = new ArrayList<String>();

        candidates1.add("testCandidate1");
        candidates2.add("testCandidate2");

        testParty1.setCandidateList(candidates1);
        testParty1.setNumCandidate(1);
        testParty2.setCandidateList(candidates2);
        testParty2.setNumCandidate(1);

        parties[0] = testParty1;
        parties[1] = testParty2;
        ArrayList<Party> tiedParties = new ArrayList<Party>();
        tiedParties.add(testParty1);
        tiedParties.add(testParty2);

        testParty1.setInitialBallotCount(1);
        testParty2.setInitialBallotCount(1);
        testParty1.incSeatsWon(1);
        testParty2.incSeatsWon(1);
        testParty1.setBallotCount(1);
        testParty2.setBallotCount(1);

        test.writeElectionWinners(parties);
        test.close();
    }
    


    
}
