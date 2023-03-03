package model;

/**
 * Customers class provides customers objects
 */
public class Customers {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;
    private String state;
    private String country;
    private int countryId;

    public Customers(int customerId, String customerName, String customerAddress, String postalCode, String phoneNumber, int divisionId, String state, String country, int countryId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.state = state;
        this.country = country;
        this.countryId = countryId;
    }

    /** @return customer id */
    public int getCustomerId() {
        return customerId;
    }

    /** @param customerId set customer id */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** @return customer name */
    public String getCustomerName() {
        return customerName;
    }

    /** @param customerName set customer name */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** @return customer address */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /** @param customerAddress set customer address */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /** @return customer postal code */
    public String getPostalCode() {
        return postalCode;
    }

    /** @param postalCode set customer postal code */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** @return customer phone number */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @param phoneNumber set customer phone number */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** @return customer state */
    public String getState() {
        return state;
    }

    /** @param state set customer state */
    public void setState(String state) {
        this.state = state;
    }

    /** @return customer country */
    public String getCountry() {
        return country;
    }

    /** @param country set customer country */
    public void setCountry(String country) {
        this.country = country;
    }

    /** @return customer division id */
    public int getDivisionId() {
        return divisionId;
    }

    /** @return customer country id */
    public int getCountryId() {
        return countryId;
    }

    /** @param countryId set customer country id */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** @param divisionId set customer division id */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** @return customer name
     * override toString() method
     */
    public String toString() {
        return (customerName);
    }
}
