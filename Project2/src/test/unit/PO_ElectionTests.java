package test.unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

import main.PO_Election;

public class PO_ElectionTests {
    @Test
    public void testPO_ElectionSingleFileConstructor() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("../testing/unit/PO_Election/small-PO.csv"));
        fileScanner.nextLine();

        PO_Election election = new PO_Election(fileScanner, "11-11-1111");
        
        assertNotEquals(null, election.getElectionFile());
        assertEquals(9, election.getNumBallots());
    }

    @Test
    public void testPO_ElectionMultiFileConstructor() throws FileNotFoundException {
        Scanner[] fileScanners = new Scanner[]{ new Scanner(new File("../testing/unit/PO_Election/small-PO.csv")),
                                                new Scanner(new File("../testing/unit/PO_Election/small-PO.csv"))};

        fileScanners[0].nextLine();

        PO_Election election = new PO_Election(fileScanners, "11-11-1111");
        
        assertNotEquals(null, election.getElectionFiles());
        assertEquals(18, election.getNumBallots());
    }

    @Test
    public void testRun() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("../testing/unit/PO_Election/small-PO.csv"));
        fileScanner.nextLine();

        PO_Election election = new PO_Election(fileScanner, "11-11-1111");
        election.run();
        
        assertEquals("Pike", election.getWinningCandidate().getName());
        assertEquals("D", election.getWinningCandidate().getParty());

        Scanner[] fileScanners = new Scanner[]{ new Scanner(new File("../testing/unit/PO_Election/small-PO.csv")),
                                                new Scanner(new File("../testing/unit/PO_Election/small-PO.csv"))};
        fileScanners[0].nextLine();

        election = new PO_Election(fileScanners, "11-11-1111");
        election.run();

        assertEquals("Pike", election.getWinningCandidate().getName());
        assertEquals("D", election.getWinningCandidate().getParty());
    }

    @Test
    public void testDisplayElectionStats() throws IOException {
        Scanner fileScanner = new Scanner(new File("../testing/unit/PO_Election/small-PO.csv"));
        fileScanner.nextLine();

        PO_Election election = new PO_Election(fileScanner, "11-11-1111");

        ByteArrayOutputStream actualOutputStream = new ByteArrayOutputStream();
        PrintStream outCopy = System.out;
        System.setOut(new PrintStream(actualOutputStream));

        election.run();

        byte[] actualBytes = actualOutputStream.toByteArray();
        System.setOut(outCopy);

        FileInputStream expectedOutputStream = new FileInputStream("../testing/unit/PO_Election/small-PO-output.txt");
        byte[] expectedBytes = new byte[actualBytes.length];
        expectedOutputStream.read(expectedBytes);
        expectedOutputStream.close();

        assertArrayEquals(expectedBytes, actualBytes);

    }
}
