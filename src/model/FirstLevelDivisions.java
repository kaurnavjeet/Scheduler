package model;

/**
 * FirstLevelDivisions class provides FirstLevelDivisions objects
 */
public class FirstLevelDivisions {
    private int divisionId;
    private String division;
    private int countryId;

    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /** @return division id */
    public int getDivisionId() {
        return divisionId;
    }

    /** @param divisionId set division id */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** @return division */
    public String getDivision() {
        return division;
    }

    /** @param division set division */
    public void setDivision(String division) {
        this.division = division;
    }

    /** @return division country id */
    public int getCountryId() {
        return countryId;
    }

    /** @param countryId set division country id */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** @return division
     * override toString() method
     */
    public String toString() {
        return (division);
    }
}
