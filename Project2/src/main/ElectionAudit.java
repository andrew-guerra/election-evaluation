package main;

/**
 * ElectionAudit is an abstract class for representing an audit for an election
 * 
 * @author Andrew Guerra
 */
public abstract class ElectionAudit {
    
    private String date;

    /**
     * Gets date of election
     * 
     * @return date of election
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date of election
     * 
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns tring stating winners of an election
     * 
     * @return string stating winners of an election
     */
    public abstract String getWinners();

    /**
     * Writes audit data to audit file
     */
    public abstract void writeToAudit();

    /**
     * Returns audit
     */
    public abstract void returnAudit();
}
