package Project1.src;

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
     * 
     * @return string stating winners of an election
     */
    public abstract String getWinners();

    /**
     * 
     * @return writes audit data to audit file
     */
    public abstract void writeToAudit();

    /**
     * @return 
     */
    public abstract void returnAudit();
}