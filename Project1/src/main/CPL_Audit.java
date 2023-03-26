package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * CPL_Audit is a class that handles writing information regarding CPL_Election to an audit file. It creates an audit file and uses a FileWriter
 * to add information to the file. Each method writes some sequence of attributes to a file under the assumption the file has been opened. CPL_Audit
 * finishes when the close() method is invoked which closes the FileWriter.
 * 
 * @author  Evan Bagwell
 */

public class CPL_Audit {

    private String date;                // attributes
    private String filename;
    private File auditFile = null;
    private FileWriter auditWriter = null;
    
    /**
     * Instantiates a CPL_Audit object. It is passed a String representing the date and creates a new file in the format
     * CPL_date.txt. A FileWriter is also instantiated and is passed the new file as a parameter. 
     * 
     * @param date  String reprsentation of the date
     */
    public CPL_Audit(String date) {
        this.date = date;
        this.filename = "CPL_" + date + ".txt";
        auditFile = new File(filename);                 // instantiate FileWriter object given filename
        try {                                           // error check on file opening
            auditWriter = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("auditWriter error");
            e.printStackTrace();
        }
    }
    /**
     * Writes header information for CPL_Election to the audit file. Writes each party and candidates associated with each party.
     * Writes the total seats to be allocated, the number of ballots cast, and the quota. 
     * 
     * @param electionType  String representation of the election type
     * @param parties       Array of Party objects that are in a CPL_Election
     * @param numBallots    number of ballots cast in a CPL_Election
     * @param numSeats      number of seats up for election
     * @throws IOException
     */
    public void writeHeaderToFile(String electionType, Party[] parties, int numBallots, int numSeats) throws IOException {
        auditWriter.write(electionType + " Election Audit" + "\n\n");
        auditWriter.write("Parties\n");
        for (int i = 0; i < parties.length; i++) {  // iterate through list of parties
            auditWriter.write(parties[i].getPartyName() + " - " + parties[i].getNumCandidate() + " candidates: ");
            if (parties[i].getCandidateList().size() != 0) {    // iterate through list of candidates for party
                int j;
                for (j = 0; j < parties[i].getCandidateList().size() - 1; j++) {
                    auditWriter.write(parties[i].getCandidateList().get(j) + ",");
                }
                auditWriter.write(parties[i].getCandidateList().get(j));
            }
            auditWriter.write("\n");
        }
        // CPL_Election attributes
        auditWriter.write("\n");
        auditWriter.write("Total # of Seats to be Allocated: " + numSeats + "\n");
        auditWriter.write("Total # of Ballots Cast: " + numBallots + "\n");
        auditWriter.write("Quota: " + numBallots/numSeats + "\n\n");
    }
    /**
     * Writes initial tally of vote per party information for the CPL_Election to the audit file. Writes information for each party
     * regarding how many votes recieved including what ballots they recieved, and percentage of votes recieved.
     * 
     * @param parties       Array of Party objects that are in a CPL_Election
     * @param numBallots    number of ballots cast in a CPL_Election
     * @throws IOException
     */
    public void writeInitialPartyVotes(Party[] parties, int numBallots) throws IOException { 

        DecimalFormat df = new DecimalFormat();         // DecimalFormat object for formatting winning percentage of votes
        df.setMaximumFractionDigits(2);

        auditWriter.write("Votes by Party\n");
        for (int i = 0; i < parties.length; i++) {      // iterate through list of parties
            int linebreak = 0;
            auditWriter.write(parties[i].getPartyName() + " - recieved %" + df.format(((double)parties[i].getInitialBallotCount()/numBallots) * 100) + " of all votes with " + parties[i].getInitialBallotCount() + " votes.\n");
            auditWriter.write(parties[i].getPartyName() + " - recieved " + parties[i].getInitialBallotCount() + " votes.\n");
            auditWriter.write("Associated Ballot ID's: \n");
            ArrayList<CPL_Ballot> temp = parties[i].getBallots();
            if (temp != null) {                         // iterate through list of ballots associated with party
                for (int j = 0; j < temp.size(); j++) {
                    auditWriter.write("#" + temp.get(j).getBallotNum() + " ");
                    linebreak++;
                    if (linebreak >= 40) {              // line break after certain number of ballots on a line
                        auditWriter.write("\n");
                        linebreak = 0;
                    }
                }
            } else {
                auditWriter.write("N/A");
            }
            
            auditWriter.write("\n");
        }
        auditWriter.write("\n");
    }
    /**
     * Writes first round of seat delegation information for the CPL_Election to the audit file. Writes information for each party
     * regarding each party on how many seats they recieved.
     * 
     * @param parties   Array of Party objects that are in a CPL_Election
     * @throws IOException
     */
    public void writeFirstSeatsAllocation(Party[] parties) throws IOException { 
        auditWriter.write("Initial Seat Allocation\n");
        for (int i = 0; i < parties.length; i++) {      // iterate through list of parties
            auditWriter.write(parties[i].getPartyName() + " : " + parties[i].getSeatsWon() + " seats allocated.\n");
        }
        auditWriter.write("\n");
    }
    /**
     * Writes information regarding remaining ballots outstanding for each party for the CPL_Election to the audit file. Writes information
     * regarding how many seats are left to be allocated and whether a second round is needed.
     * 
     * @param parties           Array of Party objects that are in a CPL_Election
     * @param availableSeats    Seats that are left to be allocated
     * @throws IOException
     */
    public void writeRemainingVotes(Party[] parties, int availableSeats) throws IOException {
        auditWriter.write("Party Votes Remaining After First Round\n");
        for (int i = 0; i < parties.length; i++) {      // iterate through list of parties
            auditWriter.write(parties[i].getPartyName() + " : " + parties[i].getBallotCount() + " votes remaining.\n");
        }
        // remaining seat attributes
        auditWriter.write("\n");
        auditWriter.write("Remaining Seats Outstanding - " + availableSeats + "\n");
        if (availableSeats == 0) {
            auditWriter.write("Remainder Seat Distribution Unneeded.\n");
        } else {
            auditWriter.write("Remainder Seat Distribution Needed.\n");
        }
        auditWriter.write("\n");
    }
    /**
     * Writes information regarding a list of parties that have the largest remainder of ballots in remainder allocation for the CPL_Election
     * to the audit file. It writes the current number of seats left to be allocated, the number of ballots outstanding, and the parties
     * for which have that number of ballots outstanding.
     * 
     * @param tiedParties       List of parties that have the highest number of remaining ballots.
     * @param remainder         Number of remaining ballots.
     * @param availableSeats    Seats that are left to be allocated.
     * @throws IOException
     */
    public void writeGreatestRemainderParty(ArrayList<Party> tiedParties, int remainder, int availableSeats) throws IOException {
        auditWriter.write("Seats Remaining to be Allocated: " + availableSeats + "\n");
        auditWriter.write("Largest Vote Remainder: " + remainder + "\n");
        auditWriter.write("Party(s) With largest Vote Remainder: ");
        for (int i = 0; i < tiedParties.size(); i++) {      // iterate through list of parties
            auditWriter.write(tiedParties.get(i).getPartyName());
            if (i != tiedParties.size() - 1) {
                auditWriter.write(", ");
            }
        }
        auditWriter.write("\n\n");
        auditWriter.write("Seat Allocation Result: \n");
    }
    /**
     * Writes information regarding the success of attempting to allocate an additional seat to a party for the CPL_Election
     * to the audit file. If status is 0, a seat is to be successfully allocated. If status is 1, a seat is to be successfully allocated via a coin toss. If status
     * is 2, a seat is not successfully allocated as party does not have enough candidates. The number of total seats the party is
     * won in current iteration is also written to the audit file
     * 
     * @param p         Party object to potentially allocate a seat to.
     * @param status    status of attempting to allocate additional seat to party.
     * @throws IOException
     */
    // status: 0 if successful, 1 if successful (won coin toss), 2 if failure (not enough candidates) 
    public void writeRemainingSeatsAllocated(Party p, int status) throws IOException {    
        if (status == 0) {      // successful seat allocation
            auditWriter.write(p.getPartyName() + " - Successfully allocated one seat\n");
        }
        if (status == 1) {      // successful seat allocation (won coin toss)
            auditWriter.write(p.getPartyName() + " - Successfully allocated one seat (won coin toss)\n");
        }
        if (status == 2) {      // unsuccessful seat allocation (not enough candidates)
            auditWriter.write(p.getPartyName() + " - Failed to allocate extra seat (not enough candidates)\n");
        }
        auditWriter.write("Total seats won by party: " + p.getSeatsWon() + "\n\n");
    }
    /**
     * Writes that all remaining votes have been considered in a CPL_Election to the audit file. Writes the number of seats
     * left to be allocated.
     * 
     * @param availableSeats    Seats that are left to be allocated.
     * @throws IOException
     */
    public void extraSeats(int availableSeats) throws IOException {
        auditWriter.write("All remaining votes have been considered. Allocating remaining " + availableSeats + " seats by coin toss.\n\n");
    }
    /**
     * Writes total seat distribution of a CPL_Eleciton to the audit file. Writes number of seats won for each party.
     * 
     * @param parties   Array of Party objects that are in a CPL_Election.
     * @throws IOException
     */
    public void writeTotalSeatDistribution(Party[] parties) throws IOException {
        auditWriter.write("Total Seat Distribution\n");
        for (int i = 0; i < parties.length; i++) {      // iterate through list of parties
            auditWriter.write(parties[i].getPartyName() + " : " + parties[i].getSeatsWon() + " seats allocated.\n");
        }
        auditWriter.write("\n");
    }
    /**
     * Writes election winners of a CPL_Election to the audit file. Writes each party and which candidate won the seat.
     * 
     * @param parties   Array of Party objects that are in a CPL_Election.
     * @throws IOException
     */
    public void writeElectionWinners(Party[] parties) throws IOException {
        auditWriter.write("Election Winners\n");
        for (int i = 0; i < parties.length; i++) {                          // iterate through list of parties
            auditWriter.write(parties[i].getPartyName() + " : ");
            for (int j = 0; j < parties[i].getSeatsWon(); j++) {            // iterate through candidates who won seats
                auditWriter.write(parties[i].getCandidateList().get(j));
                if (j != parties[i].getSeatsWon() - 1) {
                    auditWriter.write(", ");
                }
                
            }
            auditWriter.write("\n");
        }
        auditWriter.write("\n");
    }
    /**
     * Closes the FileWriter object to the audit file. Assumed file has been opened and FileWriter object initialized.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        auditWriter.close();
    }
    /**
     * get the String representation of the date
     * @return  String representing the date
     */
    public String getDate() {
        return date;
    }
    /**
     * set the String representation of the date
     * @param date  String representing the date
     */
    public void setDate(String date) {
        this.date = date;
    } 
    /**
     * get the audit File of the CPL_Election
     * @return  audit File representing the CPL_Election
     */
    public File getAuditFile() {
        return this.auditFile;
    }
    /**
     * set the audit File of the CPL_Election
     * @param auditFile audit File representing the CPL_Election
     */
    public void setAuditFile(File auditFile) {
        this.auditFile = auditFile;
    }
    /**
     * get the FileWriter object of the audit file
     * @return  FileWriter object of the audit file
     */
    public FileWriter getAuditWriter() {
        return auditWriter;
    }
    /**
     * set the FileWriter object of the audit file
     * @param auditWriter   FileWriter object of the audit file  
     */
    public void setAuditWriter(FileWriter auditWriter) {
        this.auditWriter = auditWriter;
    }
    

    
}
