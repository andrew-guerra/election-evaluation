package main;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class that represents an Election and contains methods
 * set attributes of an election being the type, file name, file scanner, and number of total ballots.
 * 
 * @Authors Tyler Grimm
 */
public class Election {

    protected int numBallots;
    protected String typeElection;
    protected String fileName;
    protected Scanner electionFile = null;
    protected Scanner[] electionFiles;

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
     * @param electionFiles Scanner[], scanner[] for parsing file
     */
    public Election(Scanner[] electionFiles) {
        this.electionFiles = Arrays.copyOf(electionFiles, electionFiles.length);
    }
    /**
     * Constructor for election
     * @param electionFile Scanner, scanner for parsing file
     */
    public Election(Scanner electionFile) {
        this.electionFile = electionFile;
    }

    /**
     * run method is inherited and overrided by CPL and IR
     * @throws IOException if write fails
     * @throws PrinterException
     */
    public void run() throws IOException, PrinterException {

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

    /**
     * get the scanner[] scanning the election files
     * @return Scanner[], the scanner[] scanning the election file
     */
    public Scanner[] getElectionFiles() {
        return this.electionFiles;
    }

    /**
     * set the scanner[] scanning the election files
     * @param electionFiles Scanner[], the scanner[] scanning the election files
     */
    public void setElectionFiles(Scanner[] electionFiles) {
        this.electionFiles = electionFiles;
    }   
}

