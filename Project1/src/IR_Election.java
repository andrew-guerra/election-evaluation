package Project1.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

//import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

//import Project1.src.IR_Audit;

import java.util.Random;

//imports
// doesn't include audit object stuff

public class IR_Election extends Election {

    // attributes
    private Candidate [] candidates;
    private int numCandidates;
    private int numRemainingCandidates;
    private IR_Ballot [] currentBallots;
    private int currentBallotCount;
    private IR_Audit audit; 

    // testing
    public static void main(String[] args) throws IOException {
        Scanner electionFile;

    // what would be in main
        try {
            electionFile = new Scanner(new File("header.csv"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("crying");
            return;
        }

        electionFile.next();
        electionFile.nextLine();

        IR_Election ir = new IR_Election(electionFile);

        File audit = new File("audit_test.txt");
        FileWriter auditWriter;
        String date = "1/1/1";
        try {
            auditWriter = new FileWriter("audit_test.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("crying");
            return;
        } 
        IR_Audit auditer = new IR_Audit(date, audit, auditWriter);
        ir.setAudit(auditer);

        ir.run();
        auditWriter.close();
    // what would be in main

    // // testing read ir header
    //     System.out.println("Header Test");
    //     ir.readIRHeader();
    //     System.out.println("IR");
    //     System.out.println(ir.numCandidates);
    //     for (int i = 0; i < ir.numCandidates - 1; i++) {
    //         System.out.print(ir.candidates[i].getName() + " " +
    //         ir.candidates[i].getParty() + ", ");
    //     }
    //     System.out.println(ir.candidates[ir.numCandidates - 1].getName() + " " + ir.candidates[ir.numCandidates - 1].getParty());
    //     System.out.println(ir.currentBallotCount);

    //     // test ballots read
    //     System.out.println();
    //     System.out.println("Ballot Reads");

    //     // testing read in ir ballots
    //     ir.readIRBallots();
    //     for (int i = 0; i < ir.currentBallotCount; i++) {
    //         System.out.println(ir.currentBallots[i].getForm());
    //         for (int j = 0; j < ir.numCandidates; j++) {
    //             System.out.println(ir.currentBallots[i].getCandidateRanking()[j]);
    //         }
    //         System.out.println();
    //     };

    //     // testing for ballot allocations
    //     System.out.println();
    //     System.out.println("Ballot Allocations");
    //     ir.allocateBallots();
    //     System.out.println();

    //     for (int i = 0; i < ir.numCandidates; i++) {
    //         System.out.print(ir.candidates[i].getName() + " " + i + ": ");
    //         for (int j = 0; j < ir.candidates[i].getBallotCount(); j++) {
    //             System.out.print(ir.candidates[i].getBallots()[j].getBallotNum() + " ");
    //         }
    //         System.out.println();
    //     }

    //     // testing coin toss and loses
    //     System.out.println();
    //     System.out.println("FindLowest Test");
    //     int lowest = ir.findLowestCandidate();
    //     System.out.println(ir.candidates[lowest].getName());

    //     // remove candidate
    //     System.out.println();
    //     System.out.println("RemoveLowest Test");

    //     while(ir.numRemainingCandidates > 0) {
    //         lowest = ir.findLowestCandidate();
    //         ir.removeLowestCandidate(lowest);
    //         ir.allocateBallots();
    //         for (int i = 0; i < ir.numCandidates; i++) {
    //             if (ir.candidates[i] != null) {
    //                 System.out.print(ir.candidates[i].getName() + " " + i + ": ");
    //                 for (int j = 0; j < ir.candidates[i].getBallotCount(); j++) {
    //                     System.out.print(ir.candidates[i].getBallots()[j].getBallotNum() + " ");
    //                 }
    //                 System.out.println();
    //             }
    //         }
    //         System.out.println();
    //     }
        
    //     // test run()
    //     System.out.println();
    //     System.out.println("run()");

        //ir.run();
 
        return;
    }

    private void readIRHeader() {
        // read second line of file and get the number of candidates
        // and move scanner to the next line with candidates afterwards
        numCandidates = electionFile.nextInt();
        electionFile.nextLine();
        // number of candidates still in the running
        numRemainingCandidates = numCandidates;
        // fill in candidates fields for each candidate
        candidates = new Candidate[numCandidates];
        for (int i = 0; i < numCandidates; i++) {
            // get name and party of candidate
            String name = electionFile.next();
            String party = electionFile.next();

            //get party name in form of "(*)" where * is letter of the party
            party = party.substring(0, 3);
            //fill fields of candidates
            candidates[i] = new Candidate();
            candidates[i].setBallotCount(0);
            candidates[i].setBallots(null);
            candidates[i].setName(name);
            candidates[i].setParty(party);
        }
        electionFile.nextLine();
        // on line with ballot count
        numBallots = electionFile.nextInt();
        currentBallotCount = numBallots;
        electionFile.nextLine();
        // scanner, now on lines with ballots
        return;
    }

    // ballot num goes from 0 to total - 1, for 6 ballots, numbered 0 to 5
    private void readIRBallots() {
        IR_Ballot[] ballots = new IR_Ballot[currentBallotCount];
        for (int i = 0; i < currentBallotCount; i++) {
            // create and initalize candidate Ranking Array
            int[] candidateRanking = new int[numCandidates];
            for (int k = 0; k < numCandidates; k++) {
                candidateRanking[k] = -1;
            }

            // create new ballot and initalize rank, ballot number, and form (use constructor?)
            IR_Ballot ballot = new IR_Ballot();
            ballot.setRank(0);
            ballot.setBallotNum(i);
            ballot.setForm(electionFile.next());

            // using form, update candidate ranking array, position starts at array
            // may modify to be 1
            int position = 0;
            for (int j = 0; j < ballot.getForm().length(); j++) {
                char curChar = ballot.getForm().charAt(j);
                if (curChar == ',') {
                    position++;
                } else {
                    int rank = Character.getNumericValue(curChar);
                    candidateRanking[rank - 1] = position;
                }
            }

            //update ballots candidate ranking, and add ballot to ballots
            ballot.setCandidateRanking(candidateRanking);
            ballots[i] = ballot;
        }

        // update IR_Elections current unallocated ballots
        currentBallots = ballots;

        // initalize ballot aspect for each candidate
        for (int i = 0; i < numCandidates; i++) {
            IR_Ballot [] ballotbox = new IR_Ballot[currentBallotCount];
            candidates[i].setBallots(ballotbox);
        }

        return;
    }

    private void allocateBallots() {
        for (int i = 0; i < currentBallotCount; i++) {
            // get current ballots to allocate
            IR_Ballot ballotToAllocate = currentBallots[i];

            // get the candidate number that the ballot should go to
            int currentBallotRank = ballotToAllocate.getRank();
            int candidateNum = ballotToAllocate.getCandidateAtNum(currentBallotRank);

            // give ballot to candidate if he is still in the running
            if (candidates[candidateNum] != null) {
                candidates[candidateNum].addBallot(ballotToAllocate);
            }
        }
        // reset current ballot objects
        currentBallots = null;
        currentBallotCount = 0;
        return;
    }

    private int coinToss(int[] tieFolk) {
        Random random = new Random();
        int randomNumber = random.nextInt(tieFolk.length);
        return tieFolk[randomNumber];
    }

    private int findLowestCandidate() throws IOException {

        int remove = 0;
        while (candidates[remove] == null && remove < numCandidates) {
            remove++;
        }

        int lowestVote = candidates[remove].getBallotCount();

        // number of candidates with the lowest votes
        int numberOfLowest = 1;

        // find the lowest Vote Count,
        for (int i = 0; i < numCandidates; i++) {
            if (candidates[i] != null && candidates[i].getBallotCount() < lowestVote) {
                lowestVote = candidates[i].getBallotCount();
                remove = i;
                numberOfLowest = 1;
            } else if (candidates[i] != null && candidates[i].getBallotCount() == lowestVote) {
                numberOfLowest++;
            }
        }

        // create remove array if more than one tie folk, else wise return the index of the lowest
        if (numberOfLowest == 1) {
            return remove;
        }

        // if more than one with same lowest number of votes
        int[] tieFolk = new int[numberOfLowest];
        int tieIndex = 0;
        for (int i = 0; i < numCandidates; i++) {
            if (candidates[i] != null && candidates[i].getBallotCount() == lowestVote) {
                tieFolk[tieIndex] = i;
                tieIndex++;
            }
        }
        
        // call tieFolk to pick out a loser
        audit.writeTiedLoserCandidates(candidates, tieFolk);
        return coinToss(tieFolk);
    }


    private void removeLowestCandidate(int lowest) {
        for (int i = 0; i < candidates[lowest].getBallotCount(); i++) {
            int curRank = candidates[lowest].getBallots()[i].getRank();
            candidates[lowest].getBallots()[i].setRank(curRank + 1);
        }
        currentBallots = candidates[lowest].getBallots();
        currentBallotCount = candidates[lowest].getBallotCount();
        candidates[lowest] = null;
        numRemainingCandidates -= 1;
        return;
    }

    private int checkMajority() throws IOException {
        if (numRemainingCandidates <= 2) {
            return runPopularity();
        }

        for (int i = 0; i < numCandidates; i++) {
            if (candidates[i] != null && candidates[i].getBallotCount() > numBallots/2) {
                return i;
            }
        }

        return -1;
    }

    private int runPopularity() throws IOException {
        int winner = 0;
        while (candidates[winner] == null) {
            winner++;
        }
        int highestVote = candidates[winner].getBallotCount();

        for (int i = winner + 1; i < numCandidates; i++) {
            if (candidates[i] != null && candidates[i].getBallotCount() > highestVote) {
                winner = i;
            } else if (candidates[i] != null && candidates[i].getBallotCount() == highestVote) {
                int[] tieFolk = {winner, i};
                //write tie
                audit.writeTiedWinnerCandidates(candidates, tieFolk);
                winner = coinToss(tieFolk);
            }
        }
        return winner;
    }

    public void run() throws IOException {
        //electionFile.reset();
        readIRHeader();
        this.audit.writeHeaderToFile(this.typeElection, this.numCandidates, this.candidates, this.numBallots);
        readIRBallots();
        //shuffleBallots();
        shuffleBallots();
        while(true) {
            allocateBallots();
            audit.writeCandidatesBallots(candidates, numCandidates);
            int winner = checkMajority();
            if (winner != -1) {
                // write to audit
                audit.writeWinner(candidates, winner);

                // display winner
                System.out.print("Candidate Winner: " + candidates[winner].getName());
                System.out.println(" " + candidates[winner].getParty());
                double percentage = ((double)candidates[winner].getBallotCount()/numBallots) * 100;
                System.out.println("Votes: " + candidates[winner].getBallotCount());
                System.out.println("Vote Percentage: " + String.format("%.2f",percentage));

                return;
            }
            int lowest = findLowestCandidate();
            audit.writeLoser(candidates, lowest);
            removeLowestCandidate(lowest);
            audit.writeBallotsReallocated(this.currentBallots, this.currentBallotCount);
            //write to audit
        }
    }

    public void shuffleBallots() {
        Random rnd = ThreadLocalRandom.current();
        for (int i = currentBallots.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            IR_Ballot a = currentBallots[index];
            currentBallots[index] = currentBallots[i];
            currentBallots[i] = a;
        }
    }





    // constructers
    public IR_Election(Scanner electionFile) {
        super(electionFile);
        //readIRHeader();
    }

    // getters and setters
    public Candidate[] getCandidates() {
        return this.candidates;
    }

    public void setCandidates(Candidate[] candidates) {
        this.candidates = candidates;
    }

    public int getNumCandidates() {
        return this.numCandidates;
    }

    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }

    public int getNumRemainingCandidates() {
        return this.numRemainingCandidates;
    }

    public void setNumRemainingCandidates(int numRemainingCandidates) {
        this.numRemainingCandidates = numRemainingCandidates;
    }

    public IR_Ballot[] getCurrentBallots() {
        return this.currentBallots;
    }

    public void setCurrentBallots(IR_Ballot[] currentBallots) {
        this.currentBallots = currentBallots;
    }

    public int getCurrentBallotCount() {
        return this.currentBallotCount;
    }

    public void setCurrentBallotCount(int currentBallotCount) {
        this.currentBallotCount = currentBallotCount;
    }

    // toString
    @Override
    public String toString() {
        return "{" +
            " candidates='" + getCandidates() + "'" +
            ", numCandidates='" + getNumCandidates() + "'" +
            ", numRemainingCandidates='" + getNumRemainingCandidates() + "'" +
            ", currentBallots='" + getCurrentBallots() + "'" +
            ", currentBallotCount='" + getCurrentBallotCount() + "'" +
            "}";
    }

    public IR_Audit getAudit() {
        return this.audit;
    }

    public void setAudit(IR_Audit audit) {
        this.audit = audit;
    }

}

