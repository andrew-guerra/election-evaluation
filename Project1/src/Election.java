package Project1.src;

import java.io.IOException;
import java.util.Scanner;

public class Election {

    protected int numBallots;
    protected String typeElection;
    protected String fileName;
    protected Scanner electionFile;

    public Election(Scanner electionFile) {
        this.electionFile = electionFile;
    }
    public void run() throws IOException {
        
    }

    public int getNumBallots() {
        return this.numBallots;
    }

    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }

    public String getTypeElection() {
        return this.typeElection;
    }

    public void setTypeElection(String typeElection) {
        this.typeElection = typeElection;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Scanner getElectionFile() {
        return this.electionFile;
    }

    public void setElectionFile(Scanner electionFile) {
        this.electionFile = electionFile;
    }   

}