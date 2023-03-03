package model;

/**
 * Countries class provides countries objects
 */
public class Countries {
    private int countryId;
    private String country;

    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /** @return country id */
    public int getCountryId() {
        return countryId;
    }

    /** @param countryId set country id */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** @return country */
    public String getCountry() {
        return country;
    }

    /** @param country set country */
    public void setCountry(String country) {
        this.country = country;
    }

    /** @return country
     * override toString() method
     */
    public String toString() {
        return (country);
    }
}
