package Project1.src.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import Project1.src.CPL_Election;
import Project1.src.Election;
import Project1.src.IR_Election;
import Project1.src.Main;

public class MainTests {
    @Test
    public void testRetrieveFilename() {
        String[] args = new String[]{};
        Scanner input;
        try  {
            input = new Scanner(new File("../testing/unit/Main/filenames.txt"));
        } catch (FileNotFoundException e) {
            fail("filenames.txt not found");
            input.close();
            return;
        }

        String filename = Main.retrieveFilename(args, input);
        assertEquals("filename.txt", filename);

        args = new String[]{"filename.txt"};
        filename = Main.retrieveFilename(args, input);
        assertEquals("filename.txt", filename);

        input.close();
    }

    @Test
    public void testLoadElectionFile() {
        Scanner electionFileScanner = Main.loadElectionFile("../testing/unit/Main/IR_Election.csv");
        assertNotEquals(null, electionFileScanner);

        electionFileScanner.close();
    }

    @Test
    public void testRetrieveDate() {
        Scanner datesScanner, datesCorrectScanner; 

        try {
            datesScanner = new Scanner(new File("../testing/unit/Main/dates.txt"));
        } catch (Exception e) {
            fail("dates.txt not found");
            return;
        }

        try {
            datesCorrectScanner = new Scanner(new File("../testing/unit/Main/dates-out.txt"));
        } catch (Exception e) {
            fail("dates.txt not found");
            return;
        }

        String expectedDate, recievedDate;
        while(datesCorrectScanner.hasNextLine() && datesScanner.hasNextLine()) {
            expectedDate = datesCorrectScanner.nextLine().strip();
            recievedDate = Main.retrieveDate(datesScanner);

            assertEquals(expectedDate, recievedDate);
        }

        datesScanner.close();
        datesCorrectScanner.close();
    }

    @Test
    public void testRetrieveElection() {
        Scanner IRElectionFileScanner;
        try {
            IRElectionFileScanner = new Scanner(new File("../testing/unit/Main/IR_Election.csv"));
        } catch (Exception e) {
            fail("IR_Election.csv not found");
            return;
        }
        
        Election IR_election = Main.retrieveElection(IRElectionFileScanner);
        IRElectionFileScanner.close();
        assertNotEquals(null, IR_election);
        assertEquals(IR_Election.class, IR_election.getClass());

        Scanner CPLElectionFileScanner;
        try {
            CPLElectionFileScanner = new Scanner(new File("../testing/unit/Main/CPL_Election.csv"));
        } catch (Exception e) {
            fail("CPL_Election.csv not found");
            return;
        }

        Election CPL_election = Main.retrieveElection(CPLElectionFileScanner);
        CPLElectionFileScanner.close();
        assertNotEquals(null, CPL_election);
        assertEquals(CPL_Election.class, CPL_election.getClass());
    }

    @Test
    public void testMain() {
        testLoadElectionFile();
        testRetrieveDate();
        testRetrieveElection();
    }
}
