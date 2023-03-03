package model;

import java.time.LocalDateTime;

/**
 * Appointment class provides appointment objects
 */
public class Appointments {

    private int id;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;

    public Appointments(int id, String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
    }

    /** @return appointment id */
    public int getId() {
        return id;
    }

    /** @param id set appointment id */
    public void setId(int id) {
        this.id = id;
    }

    /** @return appointment title */
    public String getTitle() {
        return title;
    }

    /** @param title set appointment title */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return appointment description */
    public String getDescription() {
        return description;
    }

    /** @param description set appointment description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return appointment location */
    public String getLocation() {
        return location;
    }

    /** @param location set appointment location */
    public void setLocation(String location) {
        this.location = location;
    }

    /** @return appointment contact id */
    public int getContactId() {
        return contactId;
    }

    /** @param contactId set appointment contact id */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** @return appointment type */
    public String getType() {
        return type;
    }

    /** @param type set appointment type */
    public void setType(String type) {
        this.type = type;
    }

    /** @return appointment start */
    public LocalDateTime getStart() {
        return start;
    }

    /** @param start set appointment start */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** @return appointment end */
    public LocalDateTime getEnd() {
        return end;
    }

    /** @param end set appointment end */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** @return appointment customer id */
    public int getCustomerId() {
        return customerId;
    }

    /** @param customerId set appointment customer id */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** @return appointment user id */
    public int getUserId() {
        return userId;
    }

    /** @param userId set appointment user id */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** @return appointment type
     * override toString() method
     */
    public String toString() {
        return (type);
    }

}

