
package Project1.src;

import java.io.IOException;
import java.util.Scanner;

/**
 * File Name: Election.java
 * 
 * Description: Creates an object that represents an Election and contains methods
 * set attributes of an election being the type, file name, file scanner, and number of total ballots.
 * 
 * @Authors Tyler Grimm
 */
public class Election {

    protected int numBallots;
    protected String typeElection;
    protected String fileName;
    protected Scanner electionFile;

    /**
     * Constructor for election. Sets up attributes.
     * @param numBallots int, number of total ballots
     * @param typeElection String, type of election
     * @param fileName String, file name
     * @param electionFile Scanner, scanner parsing file
     */
    public Election(int numBallots, String typeElection, String fileName, Scanner electionFile) {
        this.numBallots = numBallots;
        this.typeElection = typeElection;
        this.fileName = fileName;
        this.electionFile = electionFile;
    }

    /**
     * Constructor for election
     * @param electionFile Scanner, scanner for parsing file
     */
    public Election(Scanner electionFile) {
        this.electionFile = electionFile;
    }

    /**
     * get the number of total ballots
     * @return int, number of total ballots
     */
    public int getNumBallots() {
        return this.numBallots;
    }

    /**
     * set the total number of ballots
     * @param numBallots int, the total number of ballots
     */
    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }

    /**
     * get the election type
     * @return String, the election type
     */
    public String getTypeElection() {
        return this.typeElection;
    }

    /**
     * set the type of election
     * @param typeElection String, the type of election
     */
    public void setTypeElection(String typeElection) {
        this.typeElection = typeElection;
    }

    /**
     * get the name of the file being read from
     * @return String, the name of the file
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * set the name of the file
     * @param fileName String, name of the file being read from
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * get the scanner scanning the election file
     * @return Scanner, the scanner scanning the election file
     */
    public Scanner getElectionFile() {
        return this.electionFile;
    }

    /**
     * set the scanner scanning the election file
     * @param electionFile Scanner, the scanner scanning the election file
     */
    public void setElectionFile(Scanner electionFile) {
        this.electionFile = electionFile;
    }   
}

