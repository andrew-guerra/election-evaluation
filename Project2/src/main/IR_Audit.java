package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class that represents an IR_Audit and contains methods
 * to print to an audit file.
 * 
 * @Author Hady Kotifani and Tyler Grimm
 */
public class IR_Audit{

    private String date; 
    private String filename;
    private File auditFile; 
    private FileWriter auditWriter; 

    /**
     * Constructor for IR_Audit. Initallizes fields filename,
     * auditFile, and audit writer. Sets date to parameter date.
     * @param date string, represents the date at which the election was run
     */
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

    /**
     * default constructor for IR_Audit. Initalizes date and file name
     * to be empty and auditFile and auditWriter to be null
     */
    public IR_Audit() {
        this.date = "";
        this.filename = "";
        this.auditFile = null;
        this.auditWriter = null;
    }

    /**
     * Writes header information about election type, total number of candidates, name of candidates, and number of total
     * ballots of an IR Election to an audit file. Does not check if candidates is null
     * orif numCandidates is out of bounds
     * @param electionType String, represents type of election
     * @param numCandidates int, represents total number of candidates
     * @param candidates, Candidate[], array of candidate objects to get name and party of candidate
     * @param numBallots int, number of total ballots sent in the election
     * @throws IOException, raised if write fails
     */
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

    /**
     * Writes what ballots are being reallocated and/or may thrown out upon next reallocation
     * to the audit file. Does not check if current ballot count is in bound and may fail if
     * too high or too low. Does not check if IR_Ballot is null
     * @param currentBallots IR_Ballot[], represents a list of ballots that will be reallocated
     * @param currentBallotCount int, represents the number of ballots that will be reallocated
     * @throws IOException raised if write failes
     */
    public void writeBallotsReallocated(IR_Ballot[] currentBallots, int currentBallotCount) throws IOException {
        // writes the ballot IDs that are being reallocated
        auditWriter.write("Reallocating Ballots Numbered: ");

        // check if any ballots are being reallocated
        if (currentBallotCount != 0) {
            // loop over all ballots being reallocated and writes their respective IDs to the audit file
            for (int i = 0; i < currentBallotCount; i++) {
                auditWriter.write("#" + currentBallots[i].getBallotNum() + " ");
            }
        } else {
            // write N/A to the audit file if no ballots are checked and sent for redistribution
            auditWriter.write("N/A");
        }

        // write ending space
        auditWriter.write("\n\n");
    } 

    /**
     * Writes candidates who are still in the running to the audit file with their vote count
     * and the respetive IDs of the ballots given to the candidates. Does not check if candidates is null
     * or if numCandidates is out of bounds, may fail
     * @param candidates Candidate[], the list of total candidates
     * @param numCandidates int, the number of total candidates
     * @throws IOException raised if write fails
     */
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

    /**
     * Writes the candidates who tied with the lowest vote to the audit file.
     * Does not check if indexes of tie folk is in bounds or if candidates or tieFolk is null
     * Will fail is so.
     * @param candidates Cadidate[], represents the list of total candidates
     * @param tieFolk int[], an array with index's that correlate to the tied candidates
     * @throws IOException raised if write fails
     */
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

    /**
     * write the names of tied candidates who tied in popularity vote
     * Only works with to candidates and with a tieFolk array of size 2.
     * Does not error check and will fail if tieFolk is greater than size
     * 2, if any idexes are out of bouds, or if either is null
     * @param candidates Cadidate[], array of all candidates
     * @param tieFolk int[], and array of ints with the indecies corresponding to the tied candidates 
     * @throws IOException raised if write fails
     */
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

    /**
     * Writes the name of the candidate removed from the audit file. Does not error check 
     * if index of loser is in bound and will fail if not in bound
     * @param candidates Candidate[], list of all candidates
     * @param loser int, index corresponding to the removed candidate
     * @throws IOException raised if write fails
     */
    public void writeLoser(Candidate[] candidates, int loser) throws IOException {
        // write out the name of the candidate removed to file
        auditWriter.write("Candidate Removed: ");
        auditWriter.write(candidates[loser].getName() + "\n\n");
    }

    /**
     * Writes the name of the candidate who won to the audit file.
     * @param candidates Cadidate[], array of all candidates
     * @param winner int, index correlating to the winning candidate
     * @throws IOException raised if write fails
     */
    public void writeWinner(Candidate[] candidates, int winner) throws IOException {
        // write out the name of the candidate who won to file
        auditWriter.write("Candidate Winner: ");
        auditWriter.write(candidates[winner].getName() + "\n\n");
    }

    /**
     * get the date
     * @return String, date of election
     */
    public String getDate() {
        return this.date;
    }

    /**
     * set the date
     * @param date, String, date of election
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * get name of audit file
     * @return String, the name of the audit file
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * set the name of the audit file
     * @param filename String, name of the audit file
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * get the file of audit file
     * @return File, the file of audit file
     */
    public File getAuditFile() {
        return this.auditFile;
    }

    /**
     * set the audit file's file
     * @param auditFile File, the file of audit file
     */
    public void setAuditFile(File auditFile) {
        this.auditFile = auditFile;
    }

    /**
     * get the audit writer
     * @return FileWriter, the object writing to the audit file
     */
    public FileWriter getAuditWriter() {
        return this.auditWriter;
    }

    /**
     * set the audit writer
     * @param auditWriter FileWriter, the object writing to the audit file
     */
    public void setAuditWriter(FileWriter auditWriter) {
        this.auditWriter = auditWriter;
    }

    /**
     * closes the auditWriter
     * @throws IOException raised if closing the auditWriter fails
     */
    public void close() throws IOException {
        auditWriter.close();
    }
}
