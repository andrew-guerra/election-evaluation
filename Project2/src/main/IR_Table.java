package main;

import java.awt.print.PrinterException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import java.lang.Object.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Component;
import java.awt.BorderLayout;

/**
 * A class that handles table creation for an IR election
 * 
 * @author  Evan Bagwell
 */
public class IR_Table {
    
    private int numBallots;
    private int roundNum;
    private Candidate[] candidates;
    private JTable ir_table;
    private JFrame frame;
    private DefaultTableModel model;
    private int[] previousVoteCounts;
    private int previousExhaust;

    /**
     * Constructor for IR_Table. Initializes state of the IR_Table. Calls createFrame and IR_Table_Initialize.
     * @param candidates is an array that holds the candidates for an IR_Election
     * @param numBallots is the number of ballots in the IR_Election
     */
    public IR_Table(Candidate[] candidates, int numBallots) {
        this.roundNum = 1;                                                  
        this.numBallots = numBallots;
        this.candidates = Arrays.copyOf(candidates, candidates.length);
        this.previousVoteCounts = new int[candidates.length];
        this.previousExhaust = 0;
        createFrame();
        model = new DefaultTableModel();
        ir_table = new JTable(model);
        IR_Table_Initialize();
    }
    /**
     * Initializes the IR_Table using DefaultTableModel to add columns and rows. Creates headers for Candidate,
     * Party and listing the candidates. Initializes exhausted pile and total rows.
     * Takes no parameters and returns nothing.
     */
    private void IR_Table_Initialize() {

        // model initial creation
        model.addColumn("Candidate");
        model.addColumn("Party");
        model.addRow(new Object[]{"Candidates", ""});
        model.addRow(new Object[]{"Candidate", "Party"});

        // insert candidate and party names into cells
        for (int i = 0; i < candidates.length; i++) {
            String cname = candidates[i].getName();
            String pname = candidates[i].getParty();
            if (cname == "") {
                cname = "No Name Candidate";
            }
            if (pname == "") {
                pname = "No Name Party";
            }
            model.addRow(new Object[]{cname, pname});
        }

        // botttom two rows creation
        model.addRow(new Object[]{"EXHAUSTED PILE", ""});
        model.addRow(new Object[]{"TOTALS", ""});

        ir_table.setShowGrid(true);
        
    }
    /**
     * Takes in the state of a round of an IR_Election and generates new columns representing the round in the
     * IR_Election. Round is determined internally. Uses state of candidates and ballot count to determine fields.
     * For each candidate, number votes and change in votes are calculated. Also sets state of total ballots and
     * exhausted pile. Returns nothing.
     * @param currCandidates is an array on candidates still in the running of an election
     */
    public void IR_Table_Round(Candidate[] currCandidates) {

        // round setup
        model.addColumn("RoundVotes");
        model.addColumn("RoundChange");
        model.setValueAt("Round " + roundNum, 0, roundNum * 2);
        model.setValueAt("Votes", 1, roundNum * 2);
        model.setValueAt("+/-", 1, roundNum * 2 + 1);

        // display total ballots in election below round one calculations
        if (roundNum == 1) {
            model.setValueAt(numBallots, candidates.length + 3, roundNum * 2);
            model.setValueAt("+" + numBallots, candidates.length + 3, roundNum * 2 + 1);
        }

        int tempTotal = 0;
        int exhaust = 0;

        // candidate current ballot calculations
        for (int i = 0; i < currCandidates.length; i ++) {
            if (currCandidates[i] != null) {                        // candidate still in contention 
                model.setValueAt(currCandidates[i].getBallotCount(), 2+i, roundNum * 2);
                tempTotal += currCandidates[i].getBallotCount();
            } else {
                if (previousVoteCounts[i] == 0) {                   // candidate was eliminated
                    model.setValueAt("-----", 2+i, roundNum * 2);
                } else {                                            // candidate was just eliminated
                    model.setValueAt(0, 2+i, roundNum * 2);
                }
            }
        }
        
        // candidate change in ballots
        for (int i = 0; i < currCandidates.length; i++) {
            if (currCandidates[i] != null) {                        // candidate still in contention                                                         
                String pm = "";
                int temp;
                if (roundNum == 1) {                                // previous vote counts not initialized
                    temp = currCandidates[i].getBallotCount(); 
                } else {
                    temp = currCandidates[i].getBallotCount() - previousVoteCounts[i];
                }
                previousVoteCounts[i] = currCandidates[i].getBallotCount();
                if (temp >= 0) {
                    pm = "+";
                }
                model.setValueAt(pm + temp, 2+i, roundNum * 2 + 1);

            } else {
                if (previousVoteCounts[i] != 0) {                   // candidate was just eliminated
                    model.setValueAt("-" + previousVoteCounts[i], 2+i, roundNum * 2 + 1);
                    previousVoteCounts[i] = 0;
                } else {                                            // candidate was eliminated
                    model.setValueAt("-----", 2+i, roundNum * 2 + 1);
                }
            }
        }
       
        // exhausted pile allocation 
        exhaust = numBallots - tempTotal;
        model.setValueAt(exhaust, candidates.length + 2, roundNum * 2);
        model.setValueAt("+" + (exhaust - previousExhaust), candidates.length + 2, roundNum * 2 + 1);
        previousExhaust = exhaust;

        // round number state counter
        roundNum++;
        
    }
    /**
     * Creates a JPanel for which the IR_Table is added to and eventually displayed with JFrame. 
     * Assumes the table has been properly created.
     * Takes no parameters and returns nothing
     * @throws PrinterException
     */
    public void IR_Table_Display() throws PrinterException {

        JPanel panel = new JPanel(new BorderLayout());
        ir_table.setEnabled(false);
        panel.add(ir_table, BorderLayout.CENTER);
        frame.getContentPane().add(panel);
        frame.setSize(800, 300);
        frame.setVisible(true);
    }
    /**
     * Creates a new JFrame to display the IR_Table as a seperate window. Stores the state internally. 
     * Takes no parameters and returns nothing.
     */
    public void createFrame() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }
    /**
     * sets JTable of an IR_Election
     * @param ir_table is a JTable that represents the table of an IR_Election
     */
    public void setTable(JTable ir_table) {
        this.ir_table = ir_table;
    }
    /**
     * gets the JTable of an IR_Election
     * @return a JTable representing the IR_Election
     */
    public JTable getTable() {
        return ir_table;
    }
    /**
     * sets the number of ballots in an IR_Election
     * @param numBallots is the number of ballots in an IR_Election
     */
    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }
    /**
     * gets the number of ballots in an IR_Election
     * @return an int representing the number of ballots
     */
    public int getNumBallots() {
        return numBallots;
    }
    /**
     * sets the round number of an IR_Election
     * @param roundNum  is the round number of an IR_Election
     */
    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }
    /**
     * gets the round number of an IR_Election
     * @return an int representing the round number
     */
    public int getRoundNum() {
        return roundNum;
    }
    /**
     * sets the candidates array of an IR_Election
     * @param candidates is an array of Candidate[] for an IR_Election
     */
    public void setCandidates(Candidate[] candidates) {
        this.candidates = candidates;
    }
    /**
     * gets the array representing the candidates in an IR_Election
     * @return array holding candidate objects
     */
    public Candidate[] getCandidates() {
        return candidates;
    }
    /**
     * sets an integer array representing vote counts of the previous round of an IR_Election
     * @param previousVoteCounts an int[] array that holds the previous vote counts
     */
    public void setPreviousVoteCounts(int[] previousVoteCounts) {
        this.previousVoteCounts = previousVoteCounts;
    }
    /**
     * gets an integer array representing vote counts of the previous round of an IR_Election
     * @return an integer array holding previous vote counts
     */
    public int[] getPreviousVoteCounts() {
        return previousVoteCounts;
    }
    /**
     * sets the integer representing the previous exhaust ballot totals
     * @param previousExhaust an integer representing the previous exhaust ballot totals
     */
    public void setPreviousExhaust(int previousExhaust) {
        this.previousExhaust = previousExhaust;
    }
    /**
     * gets the integer representing the previous exhaust ballots totals
     * @return an integer representing the previous exhaust ballot totals
     */
    public int getPreviousExhaust() {
        return previousExhaust;
    }
    
}
