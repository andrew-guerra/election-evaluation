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
        
        Scanner electionFile1 = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        Scanner electionFile2 = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        Scanner[] electionFiles = {electionFile1, electionFile2};

        Election test = new Election(electionFiles);

        electionFiles = test.getElectionFiles();
        test.setElectionFiles(electionFiles);
        electionFiles = test.getElectionFiles();
        assertTrue(electionFiles[0] != null && electionFiles[1] != null);
        
        assertEquals(electionFiles.length, 2);
    }
    // manual - Read header information from one CPL_Election file 
    @Test
    public void testCPL_ElectionHeader() throws IOException {
        
        Scanner electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine();  // throw away CPL header

        Scanner[] electionFiles = {electionFile};
        String date = "11-11-1111";
        CPL_Election test = new CPL_Election(electionFiles, date);
        test.run();

        Scanner electionFile1 = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        Scanner electionFile2 = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard2.csv"));
        electionFile1.nextLine();  // throw away CPL header

        Scanner[] electionFiles2 = {electionFile1, electionFile2};
        date = "11-11-1111";
        test = new CPL_Election(electionFiles2, date);
        test.run();
        
    }
    // automatic - Create an array of Scanner objects in main.java for CPL_Election
    @Test
    public void testMainScannerArray() throws IOException {
        
        Scanner[] electionFileScanners = Main.loadElectionFile(new String[]{"../testing/unit/Main/CPL_Election.csv", "../testing/unit/Main/CPL_Election.csv"});
        assertNotEquals(null, electionFileScanners);

        electionFileScanners[0].close();
        electionFileScanners[1].close();
        
    }
    // manual - Iterate through multiple CPL_Election files to read in ballots
    @Test
    public void testCPL_ElectionReadInBallots() throws IOException {
        
        Scanner electionFile = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        electionFile.nextLine();  // throw away CPL header
        
        Scanner[] electionFiles = {electionFile};
        String date = "11-11-1111";
        CPL_Election test = new CPL_Election(electionFiles, date);
        test.run();

        Scanner electionFile1 = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard.csv"));
        Scanner electionFile2 = new Scanner(new FileInputStream("../testing/unit/CPL_Election/CPL_Standard2.csv"));
        electionFile1.nextLine();  // throw away CPL header

        Scanner[] electionFiles2 = {electionFile1, electionFile2};
        date = "11-11-1111";
        test = new CPL_Election(electionFiles2, date);
        test.run();
    }
}