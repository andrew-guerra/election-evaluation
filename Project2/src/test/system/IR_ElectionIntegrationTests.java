package test.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

import main.Main;

public class IR_ElectionIntegrationTests {
    @Test
    public void testSmallIR_Election() throws PrinterException {
        File expectedAuditFile, actualAuditFile;
        try  {
            expectedAuditFile = new File("../testing/system/IR_ElectionIntegration/small-IR-audit.txt");
        } catch (Exception e) {
            e.printStackTrace();
            fail("small-IR-audit.txt not found");
            return;
        }

        InputStream oldIn = System.in;
        String date = "03-26-2023";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        try {
            System.setOut(new PrintStream(new File("../testing/output/testSmallIR_Election.txt")));
        } catch(Exception e) {
            e.printStackTrace();
            fail("testSmallIR_Election.txt not found");
            return;
        }

        Main.main(new String[]{"../testing/system/IR_ElectionIntegration/small-IR.csv", date});
        actualAuditFile = new File("IR_03-26-2023.txt");

        Scanner expectedAuditScanner;
        Scanner actualAuditScanner;

        try {
            expectedAuditScanner = new Scanner(expectedAuditFile);
            actualAuditScanner = new Scanner(actualAuditFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("small-IR-audit.txt or IR_03-26-2023.txt not found");
            return;
        }

        while(expectedAuditScanner.hasNextLine() && actualAuditScanner.hasNextLine()) {
            String line1 = expectedAuditScanner.nextLine().trim();
            String line2 = actualAuditScanner.nextLine().trim();
            if(line1.contains("#") && line2.contains("#")) {
                continue;
            }
            assertEquals(line1, line2);
        }

        expectedAuditScanner.close();
        actualAuditScanner.close();
        
        System.setIn(oldIn);
        System.setOut(oldOut);
    }

    @Test
    public void testLargeIR_Election() throws PrinterException {
        PrintStream oldOut = System.out;
        try {
            System.setOut(new PrintStream(new File("../testing/output/testSmallIR_Election.txt")));
        } catch(Exception e) {
            e.printStackTrace();
            fail("testSmallIR_Election.txt not found");
            return;
        }

        final long startTime = System.currentTimeMillis();
        String date = "03-26-2023";
        Main.main(new String[]{"../testing/system/IR_ElectionIntegration/large-IR.csv", date});

        final long elapsedTimeMinutes = (System.currentTimeMillis() - startTime) / 60000;
        assertTrue(elapsedTimeMinutes < 4.0);

        System.setOut(oldOut);
    }

    @Test
    public void testMultifileIR_Election() throws PrinterException {
        File expectedAuditFile, actualAuditFile;
        try  {
            expectedAuditFile = new File("../testing/system/IR_ElectionIntegration/multifile-IR-audit.txt");
        } catch (Exception e) {
            e.printStackTrace();
            fail("multifile-IR-audit.txt not found");
            return;
        }

        InputStream oldIn = System.in;
        String date = "11-11-1111";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        try {
            System.setOut(new PrintStream(new File("../testing/output/testMultifileIR_Election.txt")));
        } catch(Exception e) {
            e.printStackTrace();
            fail("testMultifileIR_Election.txt not found");
            return;
        }

        Main.main(new String[]{"../testing/system/IR_ElectionIntegration/small-IR.csv", "../testing/system/IR_ElectionIntegration/small-IR.csv"});
        actualAuditFile = new File("IR_11-11-1111.txt");

        Scanner expectedAuditScanner;
        Scanner actualAuditScanner;

        try {
            expectedAuditScanner = new Scanner(expectedAuditFile);
            actualAuditScanner = new Scanner(actualAuditFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("multifile-IR-audit.txt or IR_11-11-1111.txt not found");
            return;
        }

        while(expectedAuditScanner.hasNextLine() && actualAuditScanner.hasNextLine()) {
            String line1 = expectedAuditScanner.nextLine().trim();
            String line2 = actualAuditScanner.nextLine().trim();
            if(line1.contains("#") && line2.contains("#")) {
                continue;
            }
            assertEquals(line1, line2);
        }

        expectedAuditScanner.close();
        actualAuditScanner.close();
        
        System.setIn(oldIn);
        System.setOut(oldOut);
    }
}
