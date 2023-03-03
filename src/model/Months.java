package model;

/**
 * Months class provides months objects
 */
public class Months {
    private int id;
    private String month;


    public Months(int id, String month) {
        this.id = id;
        this.month = month;
    }

    /** @return month id */
    public int getId() {
        return id;
    }

    /** @param id set month id */
    public void setId(int id) {
        this.id = id;
    }

    /** @return month */
    public String getMonth() {
        return month;
    }

    /** @param month set month */
    public void setMonth(String month) {
        this.month = month;
    }

    /** @return month
     * override toString() method
     */
    public String toString() {
        return (month);
    }
}
