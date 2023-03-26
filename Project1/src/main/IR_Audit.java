package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IR_Audit{

    private String date; 
    private String filename;
    private File auditFile; 
    private FileWriter auditWriter; 

    public IR_Audit(String date) {
        // initalize the date
        this.date = date;

        // create the file name based on date and audit type 
        this.filename = "IR_" + date + ".txt";

        // create the audit file
        auditFile = new File(filename);

        // create the writer object so the file can be written into
        try {
            auditWriter = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("auditWriter error");
            e.printStackTrace();
        }
    }

    public IR_Audit() {
        this.date = "";
        this.filename = "";
        this.auditFile = null;
        this.auditWriter = null;
    }


    public void writeHeaderToFile(String electionType, int numCandidates, Candidate[] candidates, int numBallots) throws IOException{
        // write the election type to the file
        auditWriter.write("Election Type: " + electionType + "\n\n");

        // write the number of candidates to the file
        auditWriter.write("Number of Candidates: " + numCandidates + "\n\n");

        // write the name of all candidates given a list of candidates and the number of candidates
        // crashes if the numCandidates is greater than number of candidates
        auditWriter.write("Candidates: ");
        for (int i = 0; i < numCandidates - 1; i++) {
            auditWriter.write(candidates[i].getName() + " " + candidates[i].getParty() + ", ");
        }
        auditWriter.write(candidates[numCandidates - 1].getName() + " " 
                        + candidates[numCandidates -1].getParty() + "\n\n");

        // write the total ballot count to the audit file
        auditWriter.write("Total Ballot Count: " + numBallots + "\n\n");
    }


    // also prints out audit being redistributed if it is thrown out ???
    public void writeBallotsReallocated(IR_Ballot[] currentBallots, int currentBallotCount) throws IOException {
        // writes the ballot IDs that are being reallocated
        auditWriter.write("Reallocating Ballots Numbered: ");

        // check if any ballots are being reallocated
        if (currentBallotCount != 0) {
            // loop over all ballots being reallocated and writes their respective IDs to the audit file
            for (int i = 0; i < currentBallotCount; i++) {
                auditWriter.write(currentBallots[i].getBallotNum() + " ");
            }
        } else {
            // write N/A to the audit file if no ballots are check and sent for redistribution
            auditWriter.write("N/A");
        }

        // write ending space
        auditWriter.write("\n\n");
    } 

    public void writeCandidatesBallots(Candidate[] candidates, int numCandidates) throws IOException {
        // write Candidates current ballots and vote counts header
        auditWriter.write("Candidates Vote Counts and Ballots: \n");
        auditWriter.write("________________________\n\n");

        // loop over every candidate
        for(int i = 0; i < numCandidates; i++) {
            // check if the candidate exists
            if (candidates[i] != null) {
                // write out the candidates name and current vote count
                auditWriter.write(candidates[i].getName() + "'s Vote Count: " + candidates[i].getBallotCount() + "\n");

                // write out the ballot IDs of each ballot that each candidate has
                auditWriter.write(candidates[i].getName() + "'s Ballots: ");

                // check if a candidate has any ballots
                if (candidates[i].getBallotCount() != 0) {
                    // loop over all ballots a candidate has and prints out its ID number
                    for(int j = 0; j < candidates[i].getBallotCount(); j++) {
                        auditWriter.write("#" + candidates[i].getBallots()[j].getBallotNum() + " ");
                    }
                } else {
                    // prints N/A if the candidate has zero ballots
                    auditWriter.write("N/A");
                }

                auditWriter.write("\n\n");
            }
        }

        // write footer bar to file
        auditWriter.write("________________________");
        auditWriter.write("\n\n");
    }

    public void writeTiedLoserCandidates(Candidate[] candidates, int[] tieFolk) throws IOException {
        // writes out candidates names with lowest vote header
        auditWriter.write("Candidates Tied with Lowest Votes: ");

        // loops over all indeies in tieFolk and prints out the candidates names
        for (int i = 0; i < tieFolk.length - 1; i++) {
            auditWriter.write(candidates[tieFolk[i]].getName() + ", ");
        }

        // used to format last tied candidate
        auditWriter.write(candidates[tieFolk[tieFolk.length - 1]].getName() + "\n\n");
    }


    public void writeTiedWinnerCandidates(Candidate[] candidates, int[] tieFolk) throws IOException {
        // write candidates tied with highest votes header
        auditWriter.write("Candidates Tied in Popularity: ");

        // loops over all indeies in tieFolk and prints out the candidates names
        for (int i = 0; i < tieFolk.length - 1; i++) {
            auditWriter.write(candidates[tieFolk[i]].getName() + ", ");
        }

        // used to format last tied candidate
        auditWriter.write(candidates[tieFolk[tieFolk.length - 1]].getName() + "\n\n");
    }


    public void writeLoser(Candidate[] candidates, int loser) throws IOException {
        // write out the name of the candidate removed to file
        auditWriter.write("Candidate Removed: ");
        auditWriter.write(candidates[loser].getName() + "\n\n");
    }

    public void writeWinner(Candidate[] candidates, int winner) throws IOException {
        // write out the name of the candidate who won to file
        auditWriter.write("Candidate Winner: ");
        auditWriter.write(candidates[winner].getName() + "\n\n");
    }


    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public void close() throws IOException {
        auditWriter.close();
    }

}

    
