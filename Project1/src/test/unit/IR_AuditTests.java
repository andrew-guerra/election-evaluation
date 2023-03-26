package test.unit;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import main.Candidate;
import main.IR_Ballot;
import main.IR_Election;
import main.IR_Audit;;

public class IR_AuditTests {
    @Test
    public void testIR_Audit_Deafault() {
        //
        IR_Audit audit = new IR_Audit();
        assertEquals("", audit.getDate());
        assertEquals("", audit.getFilename());
        assertEquals(null, audit.getAuditFile());
        assertEquals(null, audit.getAuditWriter());
    }

    @Test
    public void testIR_Audit_Date_and_getters_and_setters() throws IOException { 
        
        File auditFile = new File("IR_11-23-2002.txt");
        
        IR_Audit audit = new IR_Audit("11-23-2002");
        assertEquals("11-23-2002", audit.getDate());
        assertEquals("IR_11-23-2002.txt", audit.getFilename());
        assertEquals(auditFile, audit.getAuditFile());
        assertNotNull(audit.getAuditWriter());
        audit.close();

        audit.setDate("a");
        File auditFile2 = new File("a.txt");
        audit.setAuditFile(auditFile2);
        audit.setFilename("a.txt");
        audit.setAuditWriter(null);

        assertEquals("a", audit.getDate());
        assertEquals("a.txt", audit.getFilename());
        assertEquals(auditFile2, audit.getAuditFile());
        assertNull(audit.getAuditWriter());

    }

    @Test
    public void testIR_Audit_Header() throws IOException { 
        IR_Audit audit = new IR_Audit("header");
        // write many candidates to file
        Candidate a = new Candidate();
        Candidate b = new Candidate();
        Candidate c = new Candidate();

        Candidate[] candidates = {a,b,c};
        audit.writeHeaderToFile("IR", 3, candidates, 0);
        audit.close();
    }

    
    @Test
    public void testIR_Audit_BallotReallocated() throws IOException { 
        IR_Audit audit = new IR_Audit("ballot allocate many");
        // write many candidates to file
        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();

        IR_Ballot[] ballots = {a,b,c};
        audit.writeBallotsReallocated(ballots, 3);
        audit.close();
    }

    @Test
    public void testIR_Audit_writeCandidatesBallots() throws IOException { 
        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();

        IR_Ballot[] ballots = {a,b,c};
        Candidate A = new Candidate(3, ballots, "A", "1");

        IR_Ballot[] ballots2 = {a,b};
        Candidate B = new Candidate(2, ballots2, "B", "2");

        Candidate C = new Candidate();

        Candidate[] candidates = {A,B,C};

        IR_Audit audit = new IR_Audit("CandidateBallots");
        // write many candidates to file
       
        audit.writeCandidatesBallots(candidates, 3);
        audit.close();
    }

    @Test
    public void testIR_Audit_LoserTies() throws IOException {
        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();

        IR_Ballot[] ballots = {a,b,c};
        Candidate A = new Candidate(3, ballots, "A", "1");

        IR_Ballot[] ballots2 = {a,b};
        Candidate B = new Candidate(2, ballots2, "B", "2");

        Candidate C = new Candidate();

        Candidate[] candidates = {A,B,C};

        IR_Audit audit = new IR_Audit("LoserTie");
        // write many candidates to file
        int[] tieFolk = {0,1};
        audit.writeTiedLoserCandidates(candidates, tieFolk);
        int[] tieFolk2 = {0, 1, 2};
        audit.writeTiedLoserCandidates(candidates, tieFolk2);
        audit.close();

    }

    @Test
    public void testIR_Audit_WinnerTies() throws IOException {
        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();

        IR_Ballot[] ballots = {a,b,c};
        Candidate A = new Candidate(3, ballots, "A", "1");

        IR_Ballot[] ballots2 = {a,b};
        Candidate B = new Candidate(2, ballots2, "B", "2");

        Candidate C = new Candidate();

        Candidate[] candidates = {A,B,C};

        IR_Audit audit = new IR_Audit("WinnerTie");
        // write many candidates to file
        int[] tieFolk = {0,1};
        audit.writeTiedWinnerCandidates(candidates, tieFolk);
        audit.close();

    }

    @Test
    public void testIR_Audit_LosingCandidate() throws IOException {
        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();

        IR_Ballot[] ballots = {a,b,c};
        Candidate A = new Candidate(3, ballots, "A", "1");

        IR_Ballot[] ballots2 = {a,b};
        Candidate B = new Candidate(2, ballots2, "B", "2");

        Candidate C = new Candidate();

        Candidate[] candidates = {A,B,C};

        IR_Audit audit = new IR_Audit("Loser");
        // write many candidates to file
        int loser = 1;
        audit.writeLoser(candidates, 1);
        audit.close();

    }

    @Test
    public void testIR_Audit_WinningCandidate() throws IOException {
        IR_Ballot a = new IR_Ballot();
        IR_Ballot b = new IR_Ballot();
        IR_Ballot c = new IR_Ballot();

        IR_Ballot[] ballots = {a,b,c};
        Candidate A = new Candidate(3, ballots, "A", "1");

        IR_Ballot[] ballots2 = {a,b};
        Candidate B = new Candidate(2, ballots2, "B", "2");

        Candidate C = new Candidate();

        Candidate[] candidates = {A,B,C};

        IR_Audit audit = new IR_Audit("Winner");
        // write many candidates to file
        audit.writeWinner(candidates, 1);
        audit.close();
    }
}
