package test.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

import main.Main;

public class PO_ElectionIntegrationTests {
    @Test
    public void testSmallPO_Election() {
        InputStream oldIn = System.in;
        String date = "03-26-2023";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        try {
            System.setOut(new PrintStream(new File("../testing/output/testSmallPO_Election.txt")));
        } catch(Exception e) {
            e.printStackTrace();
            fail("testSmallPO_Election.txt not found");
            return;
        }

        try {
            Main.main(new String[]{"../testing/system/PO_ElectionIntegration/small-PO.csv", date});
        } catch (PrinterException e) {
            e.printStackTrace();
            fail("PrinterException");
            return;
        }
        
        System.setIn(oldIn);
        System.setOut(oldOut);
    }

    @Test
    public void testMultifilePO_Election() {
        InputStream oldIn = System.in;
        String date = "03-26-2023";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        try {
            System.setOut(new PrintStream(new File("../testing/output/testSmallPO_Election.txt")));
        } catch(Exception e) {
            e.printStackTrace();
            fail("testSmallPO_Election.txt not found");
            return;
        }

        try {
            Main.main(new String[]{"../testing/system/PO_ElectionIntegration/small-PO.csv", date});
        } catch (PrinterException e) {
            e.printStackTrace();
            fail("PrinterException");
            return;
        }
        
        System.setIn(oldIn);
        System.setOut(oldOut);
    }

    @Test
    public void testLargePO_Election() {
        InputStream oldIn = System.in;
        String date = "03-26-2023";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        try {
            System.setOut(new PrintStream(new File("../testing/output/testSmallPO_Election.txt")));
        } catch(Exception e) {
            e.printStackTrace();
            fail("testSmallPO_Election.txt not found");
            return;
        }

        try {
            Main.main(new String[]{"../testing/system/PO_ElectionIntegration/small-PO.csv", date});
        } catch (PrinterException e) {
            e.printStackTrace();
            fail("PrinterException");
            return;
        }
        
        System.setIn(oldIn);
        System.setOut(oldOut);
    }
}
