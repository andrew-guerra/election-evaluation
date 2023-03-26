package test.unit;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;

import main.CPL_Election;
import main.CPL_Audit;
import main.Party;
import main.CPL_Ballot;

public class CPL_ElectionTests {
    // automatic
    @Test
    public void testCPL_ElectionConstructor() throws IOException {
        // test CPL_Election constructor
        Scanner electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Dummy.csv"));
        String electionType = electionFile.nextLine().strip();
        assertEquals("CPL", electionType);
        String date = "11-11-1111";
        CPL_Election test = new CPL_Election(electionFile, date);
        CPL_Audit a = null;
        assertNotEquals(test.getAuditObject(), a);
        assertEquals(electionFile, test.getElectionFile());
        test.getAuditObject().close();
    }
    // automatic
    @Test
    public void testCPL_ElectionReadCPLHeader() throws IOException {

        Scanner electionFile;
        String date;
        CPL_Election test;

        // test CPL_Election header on CPL_Dummy.csv
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Dummy.csv"));
        electionFile.nextLine().strip();
        date = "21-11-1111";
        test = new CPL_Election(electionFile, date);
        assertEquals(1, test.getNumParties());
        assertNotNull(test.getParties());
        assertEquals(1, test.getNumParties());
        assertEquals(1, test.getNumSeats());
        assertEquals(1, test.getNumBallots());
        test.getAuditObject().close();
        electionFile.close();

        // test CPL_Election header on CPL_Standard.csv
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine().strip();
        date = "22-11-1111";
        test = new CPL_Election(electionFile, date);
        assertEquals(6, test.getNumParties());
        assertNotNull(test.getParties());
        assertEquals(3, test.getNumSeats());
        assertEquals(9, test.getNumBallots());
        test.getAuditObject().close();
        electionFile.close();

    }
    // automatic
    @Test
    public void testCPL_ElectionCandidateArray() throws IOException {

        Scanner electionFile;
        String date;
        CPL_Election test;

        // test CPL_Election CandidateArray on CPL_Dummy.csv - one candidate for one party
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Dummy.csv"));
        electionFile.nextLine().strip();
        date = "31-11-1111";
        test = new CPL_Election(electionFile, date);
        assertNotNull((test.getParties())[0]);
        assertNotNull((test.getParties())[0].getCandidateList());
        assertEquals((test.getParties())[0].getCandidateList().get(0), "candidate1");
        test.getAuditObject().close();
        electionFile.close();

        // test CPL_Election CandidateArray on CPL_Standard.csv - multiple candidates for multiple parties
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine().strip();
        date = "32-11-1111";
        test = new CPL_Election(electionFile, date);
        assertNotNull(test.getParties());
        for (int i = 0; i < test.getNumParties(); i++) {
            assertNotNull(test.getParties()[i].getCandidateList());
        }
        test.getAuditObject().close();
        electionFile.close();
    }
    // automatic
    @Test
    public void testCPL_ElectionReadCPLBallots() throws IOException {
        Scanner electionFile;
        String date;
        CPL_Election test;

        // test CPL_Election on CPL_Dummy.csv - one candidate for one party
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Dummy.csv"));
        electionFile.nextLine().strip();
        date = "41-11-1111";
        test = new CPL_Election(electionFile, date);
        assertNotNull(test.getBallots());
        assertEquals((test.getBallots())[0].getBallotNum(), 0);
        electionFile.close();

        // test CPL_Election on CPL_Standard.csv - multiple candidates for multiple parties
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine().strip();
        date = "42-11-1111";
        test = new CPL_Election(electionFile, date);
        assertNotNull(test.getBallots());
        for (int i = 0; i < test.getBallots().length; i++) {
            assertEquals((test.getBallots())[i].getBallotNum(), i); 
        }
        electionFile.close();
    }
    
    // automatic
    @Test
    public void testShuffleBallots() throws IOException {
        Scanner electionFile;
        String date;
        CPL_Election test;

        // test CPL_Election on CPL_Standard.csv - multiple candidates for multiple parties
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine().strip();
        date = "61-11-1111";
        test = new CPL_Election(electionFile, date);
        CPL_Ballot[] noShuffle = Arrays.copyOf(test.getBallots(), test.getBallots().length);
        int size1 = test.getBallots().length;

        test.shuffleBallots();
        
        assertNotNull(test.getBallots());
        int size2 = test.getBallots().length;
        CPL_Ballot[] shuffle = test.getBallots();
        assertEquals(size1, size2);
        assertFalse(Arrays.equals(noShuffle, shuffle));
        test.getAuditObject().close();
        electionFile.close();
    }
    // manual
    @Test
    public void testSetFirstSeatsAllocation() throws IOException {
        Scanner electionFile;
        String date;
        CPL_Election test;

        // test CPL_Election on CPL_Dummy.csv - one candidate for one party
        // remainder seat allocation not needed
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Dummy.csv"));
        electionFile.nextLine().strip();
        date = "71-11-1111";
        test = new CPL_Election(electionFile, date);
        test.shuffleBallots();
        boolean b = test.setFirstSeatsAllocation();
        assertEquals(b, false);
        test.getAuditObject().close();
        electionFile.close();


        // test CPL_Election on CPL_Standard.csv - multiple candidates for multiple parties
        // one party has enough votes and candidates for allocation - remainder seat allocation needed
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine().strip();
        date = "72-11-1111";
        test = new CPL_Election(electionFile, date);
        test.shuffleBallots();
        b = test.setFirstSeatsAllocation();
        assertEquals(b, true);
        test.getAuditObject().close();
        electionFile.close();

        // test CPL_Election on CPL_NotEnoughCandidates.csv - multiple candidates for multiple parties
        // one party does not have enough candidates for seats allotted - remainder seat allocation needed
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_NotEnoughCandidates.csv"));
        electionFile.nextLine().strip();
        date = "73-11-1111";
        test = new CPL_Election(electionFile, date);
        test.shuffleBallots();
        b = test.setFirstSeatsAllocation();
        assertEquals(b, true);
        test.getAuditObject().close();
        electionFile.close();
    }
    // manual
    @Test
    public void testRemainderSeatsAllocation() throws IOException {
        Scanner electionFile;
        String date;
        CPL_Election test;

        // test CPL_Election on CPL_Standard.csv - multiple candidates for multiple parties
        // one party has enough votes and candidates for allocation - remainder seat allocation needed
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine().strip();
        date = "81-11-1111";
        test = new CPL_Election(electionFile, date);
        test.shuffleBallots();
        test.setFirstSeatsAllocation();
        test.setRemainderSeatsAllocation();
        test.getAuditObject().close();
        electionFile.close();

        // test CPL_Election on CPL_NotEnoughCandidates.csv - multiple candidates for multiple parties
        // one party does not have enough candidates for seats allotted - remainder seat allocation needed
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_NotEnoughCandidates.csv"));
        electionFile.nextLine().strip();
        date = "82-11-1111";
        test = new CPL_Election(electionFile, date);
        test.shuffleBallots();
        test.setFirstSeatsAllocation();
        test.setRemainderSeatsAllocation();
        test.getAuditObject().close();
        electionFile.close();

        // test CPL_Election on CPL_AllVotesIndependent.csv - multiple candidates for multiple partiess
        // one party with one candidate recieves all votes
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_AllVotesIndependent.csv"));
        electionFile.nextLine().strip();
        date = "83-11-1111";
        test = new CPL_Election(electionFile, date);
        test.shuffleBallots();
        test.setFirstSeatsAllocation();
        test.setRemainderSeatsAllocation();
        test.getAuditObject().close();
        electionFile.close();
    }
    // manual
    @Test
    public void testCPL_ElectionRun() throws IOException {
        Scanner electionFile;
        String date;
        CPL_Election test;

        // test CPL_Election on CPL_Dummy.csv - one candidate for one party
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Dummy.csv"));
        electionFile.nextLine().strip();
        date = "51-11-1111";
        test = new CPL_Election(electionFile, date);
        test.run();
        electionFile.close();

        // test CPL_Election on CPL_Standard.csv - multiple candidates for multiple parties
        electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine().strip();
        date = "52-11-1111";
        test = new CPL_Election(electionFile, date);
        test.run();
        electionFile.close();
    }
}
