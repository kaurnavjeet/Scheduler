package model;

/**
 * Contacts class provides contacts objects
 */
public class Contacts {
    private int contactId;
    private String contactName;

    public Contacts(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /** @return contact id */
    public int getContactId() {
        return contactId;
    }

    /** @param contactId set contact id */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** @return contact name */
    public String getContactName() {
        return contactName;
    }

    /** @param contactName set contact name */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** @return contact name
     * override toString() method
     */
    public String toString() {
        return (contactName);
    }
}
