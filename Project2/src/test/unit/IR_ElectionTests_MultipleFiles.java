package test.unit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;

import main.IR_Election;
import main.Election;
import main.Main;
import main.IR_Audit;
import main.Party;
import main.IR_Ballot;

public class IR_ElectionTests_MultipleFiles {
    
    @Test
    public void testIR_Multiple_Election_headers_and_ballots() throws IOException {

        Scanner electionFile1 = new Scanner(new FileInputStream("../testing/unit/IR_Election/IR_multiFile_header1.csv"));
        Scanner electionFile2 = new Scanner(new FileInputStream("../testing/unit/IR_Election/IR_multiFile_header2.csv"));
        Scanner[] electionFiles = {electionFile1, electionFile2};

        electionFiles[0].next();
        electionFiles[0].nextLine();

        String date = "testing_multiHeader_IR";
        IR_Election ir = new IR_Election(electionFiles, date);
        ir.run();

    }

    @Test
    public void testIR_Multiple_Election_headers_stress_test() throws IOException {

        Scanner electionFile1 = new Scanner(new FileInputStream("../testing/unit/IR_Election/DoubleDigitCandidates.csv"));
        Scanner electionFile2 = new Scanner(new FileInputStream("../testing/unit/IR_Election/DoubleDigitCandidates.csv"));
        Scanner[] electionFiles = {electionFile1, electionFile2};

        electionFiles[0].next();
        electionFiles[0].nextLine();

        String date = "testing_multiIR_stress_header_and_ballot";
        IR_Election ir = new IR_Election(electionFiles, date);
        ir.run();

    }


}
