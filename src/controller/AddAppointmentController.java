package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import helper.Helper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * AddAppointmentController provides logic for the add appointment screen.
 */
public class AddAppointmentController implements Initializable {
    @FXML
    private TextField apptDescTxt;

    @FXML
    private TextField apptLocationTxt;

    @FXML
    private DatePicker apptStartDP;
    @FXML
    private DatePicker apptEndDP;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private ComboBox<Contacts> contactIDCB;

    @FXML
    private ComboBox<Customers> custIDCB;

    @FXML
    private ComboBox<Users> userIDCB;
    @FXML
    private ComboBox<LocalTime> timeApptCB;
    @FXML
    private ComboBox<LocalTime> endTimeCB;

    @FXML
    void onActionContactID(ActionEvent event) {

    }

    @FXML
    void onActionCustID(ActionEvent event) {

    }

    /** @param event Go back to main page. */
    @FXML
    public void onActionDisplayAppts(ActionEvent event) throws IOException {
        Helper.mainPage(event);
    }

    /** @param event save appointment to database if valid.
     * Validation checks for empty text fields and combo boxes.
     * Start and end date and time validation checks.
     * Overlapping appointment check of the same customer id.
     * Insert appointment to database and display main page.
     */
    @FXML
    public void onActionSaveAppt(ActionEvent event) throws IOException {

        //Input validation for empty
        if(apptTitleTxt.getText().isEmpty()) {
            Helper.alertError("Please enter a title for the appointment.");
        } else if(apptDescTxt.getText().isEmpty()) {
            Helper.alertError("Please enter a description for the appointment.");
        } else if(apptLocationTxt.getText().isEmpty()) {
            Helper.alertError("Please enter a location for the appointment.");
        } else if(apptTypeTxt.getText().isEmpty()) {
            Helper.alertError("Please enter a type for the appointment.");
        } else if(timeApptCB.getValue() == null) {
            Helper.alertError("Please select a start time for your appointment.");
        } else if(endTimeCB.getValue() == null) {
            Helper.alertError("Please select an end time for your appointment.");
        } else if(custIDCB.getValue() == null) {
            Helper.alertError("Please select a customer.");
        } else if(contactIDCB.getValue() == null) {
            Helper.alertError("Please select a contact.");
        } else if(userIDCB.getValue() == null) {
            Helper.alertError("Please select a user.");
        }
        else {
            boolean invalidAppt = false;
            //Date and start and end time values converted to LocalDateTime
            LocalDate startDate = apptStartDP.getValue();
            LocalDate endDate = apptEndDP.getValue();
            LocalTime startTime = timeApptCB.getValue();
            LocalTime endTime = endTimeCB.getValue();

            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(endDate, endTime);

            //Date and time validation
            if(start.isBefore(LocalDateTime.now())) {
                Helper.alertError("Your start date and time cannot be in the past.");
                invalidAppt = true;
            }

            if((end.isBefore(start))) {
                Helper.alertError("Your end date and time cannot be before start date and time.");
                invalidAppt = true;
            }

            if(end.isEqual(start)) {
                Helper.alertError("Your start and end cannot be the same.");
                invalidAppt = true;
            }

            if(endTime.equals(startTime)) {
                Helper.alertError("Your start and end times cannot be the same.");
                invalidAppt = true;
            }

            if(!(startDate.isEqual(endDate))) {
                Helper.alertError("Your start and end dates must be the same.");
                invalidAppt = true;
            }



            //Get all customer, contact, and user IDs from CB selection of names
            Customers customer = custIDCB.getValue();
            int customerId = customer.getCustomerId();

            Contacts contact = contactIDCB.getValue();
            int contactId = contact.getContactId();

            Users user = userIDCB.getValue();
            int userId = user.getUserId();

            //Overlap appointment check from filtered appointments by customer ID
            ObservableList<Appointments> filteredAppts = DBAppointments.getApptByCustomer(customerId);

            for(Appointments a : filteredAppts) {
                LocalDateTime startA = a.getStart();
                LocalDateTime endA = a.getEnd();

                if((startA.isAfter(start) || startA.isEqual(start)) && startA.isBefore(end)) {
                    Helper.alertError("Your appointment is overlapping with an existing appointment. \n" + "Appointment: " + a.getId() + " at " + a.getStart());
                   invalidAppt = true;
                } else if(endA.isAfter(start) && (endA.isBefore(end) || endA.isEqual(end))) {
                    Helper.alertError("Your appointment is overlapping with an existing appointment. \n" + "Appointment: " + a.getId() + " at " + a.getStart());
                   invalidAppt = true;
                } else if((startA.isBefore(start) || startA.isEqual(start)) && (endA.isAfter(end) || endA.isEqual(end))) {
                    Helper.alertError("Your appointment is overlapping with an existing appointment. \n" + "Appointment: " + a.getId() + " at " + a.getStart());
                   invalidAppt = true;
                }

            }

            //Add appointment to database if no overlap found
            if(!invalidAppt) {
                String title = apptTitleTxt.getText();
                String description = apptDescTxt.getText();
                String location = apptLocationTxt.getText();
                String type = apptTypeTxt.getText();

                DBAppointments.insert(title, description, location, type, Timestamp.valueOf(start), Timestamp.valueOf(end), customerId, contactId, userId);
                Helper.mainPage(event);
            }

        }

    }

    @FXML
    void onActionUserID(ActionEvent event) {

    }


    @FXML
    void onSelectapptStartDP(ActionEvent event) {

    }

    @FXML
    void onActionSelectTimeAppt(ActionEvent event) {

    }

    /** @param url
     * @param resourceBundle
     * Set start and end date pickers to today's date.
     * Convert EST business hours of 8am-10pm to system default time and populate start and end times combo boxes.
     * Populate customers, contacts, and users combo boxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initial set start and end DP to today's date
        apptStartDP.setValue(LocalDate.now());
        apptEndDP.setValue(LocalDate.now());

        //Convert EST business hours of 8am-10pm to system default
        LocalDate startDate = apptStartDP.getValue();
        LocalDate endDate = apptEndDP.getValue();
        ZonedDateTime startLocalDateTime = Helper.convertStartTimeZone(startDate);
        ZonedDateTime endLocalDateTime = Helper.convertEndTimeZone(endDate);

        LocalTime start = LocalTime.of(startLocalDateTime.getHour(), startLocalDateTime.getMinute());
        LocalTime end = LocalTime.of(endLocalDateTime.getHour(), endLocalDateTime.getMinute());

        //Populate time CB with times
        while(start.isBefore(end.plusSeconds(1))) {
            timeApptCB.getItems().add(start);
            endTimeCB.getItems().add(start.plusMinutes(30));
            start = start.plusMinutes(30);

        }

        //Populate customers, contacts, and users CB with names
        custIDCB.setItems(DBCustomers.getAllCustomers());
        contactIDCB.setItems(DBContacts.getAllContacts());
        userIDCB.setItems(DBUsers.getAllUsers());
    }
}
