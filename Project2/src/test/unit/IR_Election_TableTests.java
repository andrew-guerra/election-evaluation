package test.unit;

import static org.junit.Assert.*;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JTable;

import org.junit.Test;

import main.IR_Election;
import main.IR_Table;
import main.Candidate;
import main.Election;
import main.Main;
import main.IR_Audit;
import main.Party;
import main.IR_Ballot;


public class IR_Election_TableTests {
    @Test
    public void testIR_TableInitialization() throws IOException, PrinterException {
        
        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();
        IR_Ballot[] ballots = {a,b,c};
        Candidate A = new Candidate(3, ballots, "A", "1");
        IR_Ballot[] ballots2 = {a,b};
        Candidate B = new Candidate(2, ballots2, "B", "2");
        Candidate C = new Candidate();
        Candidate[] candidates = {A,B,C};

        // IR_Table
        IR_Table test = new IR_Table(candidates, 5);
        //test.IR_Table_Round();
        test.IR_Table_Display();

    }
    @Test
    public void testIR_TableGettersSetters() {
        
        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();
        IR_Ballot[] ballots = {a,b,c};
        Candidate A = new Candidate(3, ballots, "A", "1");
        IR_Ballot[] ballots2 = {a,b};
        Candidate B = new Candidate(2, ballots2, "B", "2");
        Candidate C = new Candidate();
        Candidate[] candidates = {A,B,C};

        IR_Table test = new IR_Table(candidates, 5);
        assertNotNull(test.getCandidates());
        assertEquals(test.getNumBallots(), 5);
        assertNotNull(test.getTable());
        assertNotNull(test.getPreviousVoteCounts());
        assertEquals(test.getPreviousExhaust(), 0);
        assertEquals(test.getRoundNum(), 1);

        test.setNumBallots(1);
        test.setPreviousExhaust(1);
        test.setRoundNum(2);

        assertEquals(test.getNumBallots(), 1);
        assertEquals(test.getPreviousExhaust(), 1);
        assertEquals(test.getRoundNum(), 2);

    }
    @Test
    public void testIR_TableRound() throws PrinterException {

        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();
        IR_Ballot[] ballots = {a,b,c};
        Candidate A = new Candidate(3, ballots, "A", "1");
        IR_Ballot[] ballots2 = {a,b};
        Candidate B = new Candidate(2, ballots2, "B", "2");
        Candidate C = new Candidate();
        Candidate[] candidates = {A,B,C};

        // IR_Table
        IR_Table test = new IR_Table(candidates, 5);
        test.IR_Table_Round(candidates);
        test.IR_Table_Display();
        
    }
    @Test
    public void testIR_TableElection() throws PrinterException, IOException {

        Scanner electionFile = null;
        String fileName = "../testing/unit/IR_Election/allocateBallotAdvanced.csv";
        try {
            electionFile = new Scanner(new FileInputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.printf("File \"%s\" cannot be found\n", fileName);
        } catch(Exception e) {
            e.printStackTrace();
        }

        electionFile.next();
        electionFile.nextLine();

        String date = "IR_allocateBallotAdvanced";
        IR_Election ir = new IR_Election(electionFile, date);
        ir.run();

    }
}
