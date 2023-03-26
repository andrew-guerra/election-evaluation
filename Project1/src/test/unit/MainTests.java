package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import main.CPL_Election;
import main.Election;
import main.IR_Election;
import main.Main;

public class MainTests {
    @Test
    public void testRetrieveFilename() {
        String[] args = new String[]{};
        Scanner input;
        try  {
            input = new Scanner(new File("../testing/unit/Main/filenames.txt"));
        } catch (FileNotFoundException e) {
            fail("filenames.txt not found");
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
            recievedDate = Main.retrieveDate(new String[]{}, datesScanner);

            assertEquals(expectedDate, recievedDate);
        }

        datesScanner.close();
        datesCorrectScanner.close();
    }

    @Test
    public void testRetrieveElection() {
        Scanner IRElectionFileScanner;
        String date = "03-26-2023";

        try {
            IRElectionFileScanner = new Scanner(new File("../testing/unit/Main/IR_Election.csv"));
        } catch (Exception e) {
            fail("IR_Election.csv not found");
            return;
        }
        
        Election IR_election = Main.retrieveElection(IRElectionFileScanner, date);
        IRElectionFileScanner.close();
        assertNotEquals(null, IR_election);
        assertEquals(IR_Election.class, IR_election.getClass());

        Scanner CPLElectionFileScanner;
        try {
            CPLElectionFileScanner = new Scanner(new File("../testing/unit/Main/small-CPL.csv"));
        } catch (Exception e) {
            fail("CPL_Election.csv not found");
            return;
        }

        Election CPL_election = Main.retrieveElection(CPLElectionFileScanner, date);
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
