package Project1.src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CPL_Audit {

    private String date;
    private String filename;
    private File auditFile = null;
    private FileWriter auditWriter = null;
    
    public CPL_Audit(String date) {
        this.date = date;
        this.filename = "CPL_" + date + ".txt";
        auditFile = new File(filename);
        try {
            auditWriter = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("auditWriter error");
            e.printStackTrace();
        }
    }
    public CPL_Audit() {
        this.date = "";
        this.filename = "";
        this.auditFile = null;
        this.auditWriter = null;
    }
    public String getDate() {
        return date;
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
        return auditWriter;
    }
    public void setAuditWriter(FileWriter auditWriter) {
        this.auditWriter = auditWriter;
    }
    public void writeHeaderToFile(String electionType, Party[] parties, int numBallots, int numSeats) throws IOException {
        auditWriter.write(electionType + " Election Audit" + "\n\n");
        auditWriter.write("Parties\n");
        for (int i = 0; i < parties.length; i++) {
            auditWriter.write(parties[i].getPartyName() + " - " + parties[i].getNumCandidate() + " candidates: ");
            if (parties[i].getCandidateList().size() != 0) {
                int j;
                for (j = 0; j < parties[i].getCandidateList().size() - 1; j++) {
                    auditWriter.write(parties[i].getCandidateList().get(j) + ",");
                }
                auditWriter.write(parties[i].getCandidateList().get(j));
            }
            auditWriter.write("\n");
        }
        auditWriter.write("\n");
        auditWriter.write("Total # of Seats to be Allocated: " + numSeats + "\n");
        auditWriter.write("Total # of Ballots Cast: " + numBallots + "\n");
        auditWriter.write("Quota: " + numBallots/numSeats + "\n\n");
    }
    public void writeInitialPartyVotes(Party[] parties, int numBallots) throws IOException { 

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        auditWriter.write("Votes by Party\n");
        for (int i = 0; i < parties.length; i++) {
            int linebreak = 0;
            auditWriter.write(parties[i].getPartyName() + " - recieved %" + df.format(((double)parties[i].getInitialBallotCount()/numBallots) * 100) + " of all votes with " + parties[i].getInitialBallotCount() + " votes.\n");
            auditWriter.write(parties[i].getPartyName() + " - recieved " + parties[i].getInitialBallotCount() + " votes.\n");
            auditWriter.write("Associated Ballot ID's: \n");
            ArrayList<CPL_Ballot> temp = parties[i].getBallots();
            if (temp != null) {
                for (int j = 0; j < temp.size(); j++) {
                    auditWriter.write("#" + temp.get(j).getBallotNum() + " ");
                    linebreak++;
                    if (linebreak >= 40) {
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
    public void writeFirstSeatsAllocation(Party[] parties) throws IOException { 
        auditWriter.write("Initial Seat Allocation\n");
        for (int i = 0; i < parties.length; i++) {
            auditWriter.write(parties[i].getPartyName() + " : " + parties[i].getSeatsWon() + " seats allocated.\n");
        }
        auditWriter.write("\n");
    }
    public void writeRemainingVotes(Party[] parties, int availableSeats) throws IOException {
        auditWriter.write("Party Votes Remaining After First Round\n");
        for (int i = 0; i < parties.length; i++) {
            auditWriter.write(parties[i].getPartyName() + " : " + parties[i].getBallotCount() + " votes remaining.\n");
        }
        auditWriter.write("\n");
        auditWriter.write("Remaining Seats Outstanding - " + availableSeats + "\n");
        if (availableSeats == 0) {
            auditWriter.write("Remainder Seat Distribution Unneeded.\n");
        } else {
            auditWriter.write("Remainder Seat Distribution Needed.\n");
        }
        auditWriter.write("\n");
    }
    public void writeGreatestRemainderParty(ArrayList<Party> tiedParties, int remainder, int availableSeats) throws IOException {
        auditWriter.write("Seats Remaining to be Allocated: " + availableSeats + "\n");
        auditWriter.write("Largest Vote Remainder: " + remainder + "\n");
        auditWriter.write("Party(s) With largest Vote Remainder: ");
        for (int i = 0; i < tiedParties.size(); i++) {
            auditWriter.write(tiedParties.get(i).getPartyName());
            if (i != tiedParties.size() - 1) {
                auditWriter.write(", ");
            }
        }
        auditWriter.write("\n\n");
        auditWriter.write("Seat Allocation Result: \n");
    }
    // status: 0 if successful, 1 if successful (won coin toss), 2 if failure (not enough candidates) 
    public void writeRemainingSeatsAllocated(Party p, int status) throws IOException {    
        if (status == 0) {
            auditWriter.write(p.getPartyName() + " - Successfully allocated one seat\n");
        }
        if (status == 1) {
            auditWriter.write(p.getPartyName() + " - Successfully allocated one seat (won coin toss)\n");
        }
        if (status == 2) {
            auditWriter.write(p.getPartyName() + " - Failed to allocate extra seat (not enough candidates)\n");
        }
        auditWriter.write("Total seats won by party: " + p.getSeatsWon() + "\n\n");
    }
    public void extraSeats(int availableSeats) throws IOException {
        auditWriter.write("All remaining votes have been considered. Allocating remaining " + availableSeats + " seats by coin toss.\n\n");
    }
    public void writeTotalSeatDistribution(Party[] parties) throws IOException {
        auditWriter.write("Total Seat Distribution\n");
        for (int i = 0; i < parties.length; i++) {
            auditWriter.write(parties[i].getPartyName() + " : " + parties[i].getSeatsWon() + " seats allocated.\n");
        }
        auditWriter.write("\n");
    }
    public void writeElectionWinners(Party[] parties) throws IOException {
        auditWriter.write("Election Winners\n");
        for (int i = 0; i < parties.length; i++) {
            auditWriter.write(parties[i].getPartyName() + " : ");
            for (int j = 0; j < parties[i].getSeatsWon(); j++) {
                auditWriter.write(parties[i].getCandidateList().get(j));
                if (j != parties[i].getSeatsWon() - 1) {
                    auditWriter.write(", ");
                }
                
            }
            auditWriter.write("\n");
        }
        auditWriter.write("\n");
    }
    public void close() throws IOException {
        auditWriter.close();
    }
 
    

    
}
