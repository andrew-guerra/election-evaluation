package test.unit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;

import main.CPL_Election;
import main.Election;
import main.Main;
import main.CPL_Audit;
import main.Party;
import main.CPL_Ballot;

public class CPL_ElectionTests_MultipleFiles {
    // automatic - Modify Election class to hold an array of Scanner objects
    @Test
    public void testElectionScannerArray() throws IOException {
        
        String[] files = new String[2];
        files[0] = "../testing/unit/CPL_Election/CPL_Standard.csv";
        files[1] = "../testing/unit/CPL_Election/CPL_Standard2.csv";
        Scanner[] electionFileScanners = Main.loadElectionFile(files);
        electionFileScanners[0].nextLine();

        Election test = new Election(electionFileScanners);

        electionFileScanners = test.getElectionFiles();
        test.setElectionFiles(electionFileScanners);
        electionFileScanners = test.getElectionFiles();
        assertTrue(electionFileScanners[0] != null && electionFileScanners[1] != null);
        
        assertEquals(electionFileScanners.length, 2);
    }
    // manual - Read header information from one CPL_Election file 
    @Test
    public void testCPL_ElectionHeader() throws IOException {
        
        String[] files = new String[1];
        files[0] = "../testing/unit/CPL_Election/CPL_Standard.csv";
        Scanner[] electionFileScanners = Main.loadElectionFile(files);
        electionFileScanners[0].nextLine();
        String date = "11-11-1111";
        CPL_Election test = new CPL_Election(electionFileScanners, date);

        test.run();

        files = new String[2];
        files[0] = "../testing/unit/CPL_Election/CPL_Standard.csv";
        files[1] = "../testing/unit/CPL_Election/CPL_Standard2.csv";
        electionFileScanners = Main.loadElectionFile(files);
        electionFileScanners[0].nextLine();
        date = "11-11-1111";
        test = new CPL_Election(electionFileScanners, date);

        test.run();
        
    }
    // automatic - Create an array of Scanner objects in main.java for CPL_Election
    @Test
    public void testMainScannerArray() throws IOException {
        
        String[] files = new String[1];
        files[0] = "../testing/unit/CPL_Election/CPL_Standard.csv";
        
        Scanner[] electionFileScanners = Main.loadElectionFile(files);
        assertNotEquals(null, electionFileScanners[0]);

        electionFileScanners[0].close();
        
        
    }
    // manual - Iterate through multiple CPL_Election files to read in ballots
    @Test
    public void testCPL_ElectionReadInBallots() throws IOException {
        
        String[] files = new String[1];
        files[0] = "../testing/unit/CPL_Election/CPL_Standard.csv";
        Scanner[] electionFileScanners = Main.loadElectionFile(files);
        electionFileScanners[0].nextLine();
        String date = "11-11-1111";
        CPL_Election test = new CPL_Election(electionFileScanners, date);
        test.run();

        files = new String[2];
        files[0] = "../testing/unit/CPL_Election/CPL_Standard.csv";
        files[1] = "../testing/unit/CPL_Election/CPL_Standard2.csv";
        electionFileScanners = Main.loadElectionFile(files);
        electionFileScanners[0].nextLine();
        date = "11-11-1111";
        test = new CPL_Election(electionFileScanners, date);
        test.run();
    }
}