package controller;

import DBAccess.*;
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
 * ModifyAppointmentController provides logic for the modify appointment screen.
 */
public class ModifyAppointmentController implements Initializable {
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
    private ComboBox<Contacts> contactIDCB;

    @FXML
    private ComboBox<Customers> custIDCB;

    @FXML
    private TextField apptIdTxt;

    @FXML
    private ComboBox<LocalTime> timeApptCB;
    @FXML
    private ComboBox<LocalTime> endTimeCB;

    @FXML
    private ComboBox<Users> userIDCB;

    private int index = 0;

    /** @param selectedIndex The index of the appointment that is selected in Main Page.
     * @param appointment The appointment object that is selected in Main Page to be modified.
     * Repopulating start time, end time, customer, contact, and user combo boxes after converting start and end time to system default from EST.
     * Send data of the selected appointment at the selected index back to MainPageController.
     * Three lambda expressions using forEach to loop through customers, contacts, and users to set values of combo boxes to the values of the selected appointment.
     * */
    //Lambda Expression
    public void sendData(int selectedIndex, Appointments appointment) {

        index = selectedIndex;
        apptIdTxt.setText(String.valueOf(appointment.getId()));
        apptTitleTxt.setText(appointment.getTitle());
        apptDescTxt.setText(appointment.getDescription());
        apptLocationTxt.setText(appointment.getLocation());
        apptTypeTxt.setText(appointment.getType());

        //Get start and end date time stamp of appointment to be modified
        LocalDateTime startDateTime = appointment.getStart();
        LocalDate startDate = startDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();

        LocalDateTime endDateTime = appointment.getEnd();
        LocalDate endDate = endDateTime.toLocalDate();
        LocalTime endTime = endDateTime.toLocalTime();


        //Set both Date Pickers to value of appointment selected to be modified
        apptStartDP.setValue(startDate);
        apptEndDP.setValue(endDate);

        //Set time in CB after converting to local time zone from EST
        ZonedDateTime startLocalDateTime = Helper.convertStartTimeZone(startDate);
        ZonedDateTime endLocalDateTime = Helper.convertEndTimeZone(endDate);

        LocalTime start = LocalTime.of(startLocalDateTime.getHour(), startLocalDateTime.getMinute());
        LocalTime end = LocalTime.of(endLocalDateTime.getHour(), endLocalDateTime.getMinute());

        while(start.isBefore(end.plusSeconds(1))) {
            timeApptCB.getItems().add(start);
            endTimeCB.getItems().add(start.plusMinutes(30));
            start = start.plusMinutes(30);
        }
        //Set start and end time CB to value of appointment selected to be modified
        timeApptCB.getSelectionModel().select(startTime);
        endTimeCB.getSelectionModel().select(endTime);

        //Set customer CB to value of appointment customer ID
        custIDCB.setItems(DBCustomers.getAllCustomers());

        DBCustomers.getAllCustomers().forEach(c -> {
            if(c.getCustomerId() == appointment.getCustomerId()) {
                custIDCB.setValue(c);
            }
        });

        //Set contact CB to value of appointment contact ID
        contactIDCB.setItems(DBContacts.getAllContacts());

        DBContacts.getAllContacts().forEach(c -> {
            if(c.getContactId() == appointment.getContactId()) {
                contactIDCB.setValue(c);
            }
        });

        //Set user CB to value of appointment user ID
        userIDCB.setItems(DBUsers.getAllUsers());

        DBUsers.getAllUsers().forEach(u -> {
            if(u.getUserId() == appointment.getUserId()) {
                userIDCB.setValue(u);
            }
        });


    }

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

    @FXML
    void onActionSelectTimeAppt(ActionEvent event) {

    }

    /** @param event Update appointment to database if valid.
     * Validation checks for empty text fields and combo boxes.
     * Start and end date and time validation checks.
     * Appointment id check allows appointment to be modified even if start and end times are not changed.
     * Overlapping appointment check of the same customer id.
     * Update appointment to database and display main page.
     */
    @FXML
    public void onActionUpdateAppt(ActionEvent event) throws IOException {
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
            Helper.alertError("Please select an end time for your appointment");
        } else if(custIDCB.getValue() == null) {
            Helper.alertError("Please select a customer.");
        } else if(contactIDCB.getValue() == null) {
            Helper.alertError("Please select a contact.");
        } else if(userIDCB.getValue() == null) {
            Helper.alertError("Please select a user.");
        } else {
            boolean invalidAppt = false;
            //Date, start, and end time values converted to LocalDateTime
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

            if(endTime.equals(startTime)) {
                Helper.alertError("Your start and end times cannot be the same.");
                invalidAppt = true;
            }

            if(!(startDate.isEqual(endDate))) {
                Helper.alertError("Your start and end dates must be the same.");
                invalidAppt = true;
            }


            //Get all customer, contact, and user IDs from CB selection of names
            Customers customer = custIDCB.getSelectionModel().getSelectedItem();
            int customerId = customer.getCustomerId();

            Contacts contact = contactIDCB.getSelectionModel().getSelectedItem();
            int contactId = contact.getContactId();

            Users user = userIDCB.getSelectionModel().getSelectedItem();
            int userId = user.getUserId();

            //Overlap appointment check from filtered appointments by customer ID
            ObservableList<Appointments> filteredAppts = DBAppointments.getApptByCustomer(customerId);

            for(Appointments a : filteredAppts) {
                LocalDateTime startA = a.getStart();
                LocalDateTime endA = a.getEnd();

                //Check if appointment id of selected appointment is equal to id of a in loop
                    if(((a.getId() != Integer.parseInt(apptIdTxt.getText())) && (startA.isAfter(start) || startA.isEqual(start)) && startA.isBefore(end))) {
                        Helper.alertError("Your appointment is overlapping with an existing appointment. \n" + "Appointment: " + a.getId() + " at " + a.getStart());
                       invalidAppt = true;

                    } else if((a.getId() != Integer.parseInt(apptIdTxt.getText())) && endA.isAfter(start) && (endA.isBefore(end) || endA.isEqual(end))) {
                        Helper.alertError("Your appointment is overlapping with an existing appointment. \n" + "Appointment: " + a.getId() + " at " + a.getStart());
                       invalidAppt = true;

                    } else if((a.getId() != Integer.parseInt(apptIdTxt.getText())) && (startA.isBefore(start) || startA.isEqual(start)) && (endA.isAfter(end) || endA.isEqual(end))) {
                        Helper.alertError("Your appointment is overlapping with an existing appointment. \n" + "Appointment: " + a.getId() + " at " + a.getStart());
                       invalidAppt = true;

                    }

            }

            //Update appointment in database if no overlap found
            if(!invalidAppt) {
                int apptId = Integer.parseInt(apptIdTxt.getText());
                String title = apptTitleTxt.getText();
                String description = apptDescTxt.getText();
                String location = apptLocationTxt.getText();
                String type = apptTypeTxt.getText();

                DBAppointments.update(apptId, title, description, location, type, Timestamp.valueOf(start), Timestamp.valueOf(end), customerId, contactId, userId);
                Helper.alertInfo("Your appointment was updated successfully.");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
