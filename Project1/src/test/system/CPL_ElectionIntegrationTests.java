package test.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

import main.Main;

public class CPL_ElectionIntegrationTests {
    @Test
    public void testSmallCPL_Election() {
        File expectedAuditFile, actualAuditFile;
        try  {
            expectedAuditFile = new File("../testing/system/CPL_ElectionIntegration/small-CPL-audit.txt");
        } catch (Exception e) {
            e.printStackTrace();
            fail("small-CPL-audit.csv not found");
            return;
        }

        InputStream oldIn = System.in;
        String date = "03-26-2023";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        try {
            System.setOut(new PrintStream(new File("../testing/output/testSmallCPL_Election.txt")));
        } catch(Exception e) {
            e.printStackTrace();
            fail("testSmallCPL_Election.txt not found");
            return;
        }

        Main.main(new String[]{"../testing/system/CPL_ElectionIntegration/small-CPL.csv", date});
        actualAuditFile = new File("CPL_03-26-2023.txt");

        Scanner expectedAuditScanner;
        Scanner actualAuditScanner;

        try {
            expectedAuditScanner = new Scanner(expectedAuditFile);
            actualAuditScanner = new Scanner(actualAuditFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("small-CPL.csv or CPL_03-26-2023.txt not found");
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
