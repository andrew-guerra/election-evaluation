package test.system;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.Test;

import main.Main;

public class PO_ElectionIntegrationTests {
    @Test
    public void testSmallPO_Election() throws IOException {
        InputStream oldIn = System.in;
        String date = "03-26-2023";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        ByteArrayOutputStream actualOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutputStream));
        
        try {
            Main.main(new String[]{"../testing/system/PO_ElectionIntegration/small-PO.csv"});
        } catch (PrinterException e) {
            e.printStackTrace();
            fail("PrinterException");
            return;
        }

        System.setIn(oldIn);
        System.setOut(oldOut);

        byte[] actualBytes = actualOutputStream.toByteArray();

        FileInputStream expectedOutputStream = new FileInputStream("../testing/system/PO_ElectionIntegration/small-PO-output.txt");
        byte[] expectedBytes = new byte[actualBytes.length];
        expectedOutputStream.read(expectedBytes);
        expectedOutputStream.close();

        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(actualBytes, StandardCharsets.UTF_8));
    }

    @Test
    public void testMultifilePO_Election() throws IOException {
        InputStream oldIn = System.in;
        String date = "03-26-2023";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        ByteArrayOutputStream actualOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutputStream));

        try {
            Main.main(new String[]{"../testing/system/PO_ElectionIntegration/small-PO.csv", "../testing/system/PO_ElectionIntegration/small-PO.csv"});
        } catch (PrinterException e) {
            e.printStackTrace();
            fail("PrinterException");
            return;
        }
        
        System.setIn(oldIn);
        System.setOut(oldOut);

        byte[] actualBytes = actualOutputStream.toByteArray();

        FileInputStream expectedOutputStream = new FileInputStream("../testing/system/PO_ElectionIntegration/multi-small-PO-output.txt");
        byte[] expectedBytes = new byte[actualBytes.length];
        expectedOutputStream.read(expectedBytes);
        expectedOutputStream.close();

        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(actualBytes, StandardCharsets.UTF_8));
    }

    @Test
    public void testLargePO_Election() throws IOException {
        InputStream oldIn = System.in;
        String date = "03-26-2023";
        System.setIn(new ByteArrayInputStream(date.getBytes()));

        PrintStream oldOut = System.out;
        ByteArrayOutputStream actualOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutputStream));

        try {
            Main.main(new String[]{"../testing/system/PO_ElectionIntegration/large-PO.csv"});
        } catch (PrinterException e) {
            e.printStackTrace();
            fail("PrinterException");
            return;
        }
        
        System.setIn(oldIn);
        System.setOut(oldOut);

        byte[] actualBytes = actualOutputStream.toByteArray();

        FileInputStream expectedOutputStream = new FileInputStream("../testing/system/PO_ElectionIntegration/large-PO-output.txt");
        byte[] expectedBytes = new byte[actualBytes.length];
        expectedOutputStream.read(expectedBytes);
        expectedOutputStream.close();

        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(actualBytes, StandardCharsets.UTF_8));
    }
}
