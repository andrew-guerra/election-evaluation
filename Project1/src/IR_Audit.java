package Project1.src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IR_Audit{

    // need to have type of voting
    // print number of candidates 
    // print out candiate names 
    // print out total number of ballots
    // dont need calculations 
    // print out how many votes a candidate has 
    // print out winner name
    // print out how the election progressed 
    // show the order of removal of candidates and what ballots were redistributed

    private String date; 
    private File auditFile; 
    private FileWriter auditWriter; 

    // do we create the audit object in main, do we create it in IR_Election
    public IR_Audit(String date, File auditFile, FileWriter auditWriter) {
        this.date = date;
        this.auditFile = auditFile;
        this.auditWriter = auditWriter;
    }

    public IR_Audit() {
        this.date = "";
        this.auditFile = null;
        this.auditWriter = null;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public File getAuditFile() {
        return this.auditFile;
    }

    public void setAuditFile(File auditFile) {
        this.auditFile = auditFile;
    }

    public FileWriter getAuditWriter() {
        return this.auditWriter;
    }

    public void setAuditWriter(FileWriter auditWriter) {
        this.auditWriter = auditWriter;
    }

    public void writeHeaderToFile(String electionType, int numCandidates, Candidate[] candidates, int numBallots) throws IOException{
        auditWriter.write("Election Type: " + electionType + "\n\n");
        auditWriter.write("Number of Candidates: " + numCandidates + "\n\n");
        auditWriter.write("Candidates: ");
        for (int i = 0; i < numCandidates - 1; i++) {
            auditWriter.write(candidates[i].getName() + " " + candidates[i].getParty() + ", ");
        }
        auditWriter.write(candidates[numCandidates - 1].getName() + " " 
                        + candidates[numCandidates -1].getParty() + "\n\n");
        auditWriter.write("Total Ballot Count: " + numBallots + "\n\n");
    }

    public void writeBallotsReallocated(IR_Ballot[] currentBallots, int currentBallotCount) throws IOException {
        auditWriter.write("Reallocating Ballots Numbered: ");
        if (currentBallotCount != 0) {
            for (int i = 0; i < currentBallotCount; i++) {
                auditWriter.write(currentBallots[i].getBallotNum() + " ");
            }
        } else {
            auditWriter.write("N/A");
        }
        auditWriter.write("\n\n");
    } 

    public void writeCandidatesBallots(Candidate[] candidates, int numCandidates) throws IOException {
        auditWriter.write("Candidates Vote Counts and Ballots: \n");
        auditWriter.write("________________________\n\n");
        for(int i = 0; i < numCandidates; i++) {
            if (candidates[i] != null) {
                auditWriter.write(candidates[i].getName() + "'s Vote Count: " + candidates[i].getBallotCount() + "\n");
                auditWriter.write(candidates[i].getName() + "'s Ballots: ");
                if (candidates[i].getBallotCount() != 0) {
                    for(int j = 0; j < candidates[i].getBallotCount(); j++) {
                        auditWriter.write("#" + candidates[i].getBallots()[j].getBallotNum() + " ");
                    }
                } else {
                    auditWriter.write("N/A");
                }
                auditWriter.write("\n\n");
            }
        }
        auditWriter.write("________________________\n");
        auditWriter.write("\n");
    }

    public void writeTiedLoserCandidates(Candidate[] candidates, int[] tieFolk) throws IOException {
        auditWriter.write("Candidates Tied with Lowest Votes: ");
        for (int i = 0; i < tieFolk.length - 1; i++) {
            auditWriter.write(candidates[tieFolk[i]].getName() + ", ");
        }
        auditWriter.write(candidates[tieFolk[tieFolk.length - 1]].getName() + "\n\n");
    }

    public void writeTiedWinnerCandidates(Candidate[] candidates, int[] tieFolk) throws IOException {
        auditWriter.write("Candidates Tied with Highest Votes: ");
        for (int i = 0; i < tieFolk.length - 1; i++) {
            auditWriter.write(candidates[tieFolk[i]].getName() + ", ");
        }
        auditWriter.write(candidates[tieFolk[tieFolk.length - 1]].getName() + "\n\n");
    }

    public void writeLoser(Candidate[] candidates, int loser) throws IOException {
        auditWriter.write("Candidate Removed: ");
        auditWriter.write(candidates[loser].getName() + "\n\n");
    }

    public void writeWinner(Candidate[] candidates, int winner) throws IOException {
        auditWriter.write("Candidate Winner: ");
        auditWriter.write(candidates[winner].getName() + "\n\n");
    }
}

    
