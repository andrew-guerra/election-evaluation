package test.unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import java.io.FileInputStream;

import org.junit.Test;

import main.Candidate;
import main.IR_Ballot;
import main.IR_Election;;

public class IR_Election_Tests {
    @Test
    public void testIR_Election_constructor_and_getters_and_setters() {

        Scanner electionFile = null;
        String fileName = "../testing/unit/IR_Election/header.csv";

        try {
            electionFile = new Scanner(new FileInputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
        } catch(Exception e) {
            e.printStackTrace();
        }

        electionFile.next();
        electionFile.nextLine();

        String date = "11-22-2002";
        IR_Election ir = new IR_Election(electionFile, date);

        assertNull(ir.getCandidates());
        assertEquals(0, ir.getNumCandidates());
        assertEquals(0, ir.getNumRemainingCandidates());
        assertNull(ir.getCurrentBallots());
        assertEquals(0, ir.getCurrentBallotCount());
        assertNotNull(ir.getAudit());

        Candidate A = new Candidate();
        Candidate B = new Candidate();
        Candidate[] candidates = {A,B};

        IR_Ballot C = new IR_Ballot();
        IR_Ballot D = new IR_Ballot();
        IR_Ballot[] ballots = {C,D};

        ir.setCandidates(candidates);
        ir.setNumCandidates(1);
        ir.setNumRemainingCandidates(1);
        ir.setCurrentBallots(ballots);
        ir.setCurrentBallotCount(1);
        ir.setAudit(null);

        
        assertNotNull(ir.getCandidates());
        assertEquals(1, ir.getNumCandidates());
        assertEquals(1, ir.getNumRemainingCandidates());
        assertNotNull(ir.getCurrentBallots());
        assertEquals(1, ir.getCurrentBallotCount());
        assertNull(ir.getAudit());
    }

    // manual check, run multiple time to see that every candidate wins once
    // checks that tied candidates can win, lowest are found and removed
    // properly in audit
    @Test
    public void testIR_Election_ties_and_lowest_vote() throws IOException {

        Scanner electionFile = null;
        String fileName = "../testing/unit/IR_Election/tie.csv";

        try {
            electionFile = new Scanner(new FileInputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
        } catch(Exception e) {
            e.printStackTrace();
        }

        electionFile.next();
        electionFile.nextLine();

        String date = "tie-for-irElection";
        IR_Election ir = new IR_Election(electionFile, date);
        ir.run();

    }

    // manual check, run multiple time to see that candidates
    // ballots are in a different order every time
    @Test
    public void testIR_Election_shuffles() throws IOException {

        Scanner electionFile = null;
        String fileName = "../testing/unit/IR_Election/shuffle_ir.csv";

        try {
            electionFile = new Scanner(new FileInputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
        } catch(Exception e) {
            e.printStackTrace();
        }

        electionFile.next();
        electionFile.nextLine();

        String date = "shuffle-for-irElection";
        IR_Election ir = new IR_Election(electionFile, date);
        ir.run();

    }

    // manual checks, check that candidate wins by majority
    @Test
    public void testIR_Election_Majority_test() throws IOException {

        Scanner electionFile = null;
        String fileName = "../testing/unit/IR_Election/majority.csv";

        try {
            electionFile = new Scanner(new FileInputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
        } catch(Exception e) {
            e.printStackTrace();
        }

        electionFile.next();
        electionFile.nextLine();

        String date = "majority-for-irElection";
        IR_Election ir = new IR_Election(electionFile, date);
        ir.run();
    }

    
    // manual checks, run multiple time to see that candidates
    // win by plurality with two candidates
    @Test
    public void testIR_Election_plurality_test() throws IOException {

        Scanner electionFile = null;
        String fileName = "../testing/unit/IR_Election/plurality.csv";

        try {
            electionFile = new Scanner(new FileInputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
        } catch(Exception e) {
            e.printStackTrace();
        }

        electionFile.next();
        electionFile.nextLine();

        String date = "plurality-for-irElection";
        IR_Election ir = new IR_Election(electionFile, date);
        ir.run();
    }

    
    // manual checks, ensure ballots are allocated properly
    @Test
    public void testIR_Election_allocation_test() throws IOException {

        Scanner electionFile = null;
        String fileName = "../testing/unit/IR_Election/allocation.csv";
        try {
            electionFile = new Scanner(new FileInputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
        } catch(Exception e) {
            e.printStackTrace();
        }

        electionFile.next();
        electionFile.nextLine();

        String date = "allocation-for-irElection";
        IR_Election ir = new IR_Election(electionFile, date);
        ir.run();
    }
}

