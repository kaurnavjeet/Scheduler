package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import helper.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import model.Contacts;
import model.Months;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * ReportsController provides logic for the Reports screen.
 */
public class ReportsController implements Initializable {
    @FXML
    private TableColumn<?, ?> apptContactId;

    @FXML
    private TableColumn<?, ?> apptCustomerId;

    @FXML
    private TableColumn<?, ?> apptDescription;

    @FXML
    private TableColumn<?, ?> apptEnd;

    @FXML
    private TableColumn<?, ?> apptId;

    @FXML
    private TableColumn<?, ?> apptLocation;

    @FXML
    private TableColumn<?, ?> apptStart;

    @FXML
    private TableColumn<?, ?> apptTitle;

    @FXML
    private TableColumn<?, ?> apptType;

    @FXML
    private TableColumn<?, ?> apptUserId;

    @FXML
    private TableView<Appointments> apptsTableView;

    @FXML
    private ComboBox<Appointments> reportsTypeCbox;

    @FXML
    private ComboBox<Months> selectApptMonthCB;

    @FXML
    private ComboBox<Contacts> selectContactCB;

    @FXML
    private ComboBox<Months> selectContactMonthCB;

    @FXML
    private Label totalContactsLbl;

    @FXML
    private Label totalCustomersLbl;

    /** @param event Resets combo box after a selection is made. */
    @FXML
    public void onActionApptMonthCB(ActionEvent event) {
        onSelectCustGo(null);

    }

    /** @param event Go back to main page. */
    @FXML
    public void onActionBackBttn(ActionEvent event) throws IOException {
        Helper.mainPage(event);
    }

    /** @param event Populates reports tableview if contact or contact and month combo boxes have a selection.
     *  Set total appointments label to number of appointments found filtered by selection.
     */
    @FXML
    public void onSelectContactsGo(ActionEvent event) {
        if(selectContactCB.getValue() != null) {
            int contactId = selectContactCB.getValue().getContactId();
            if(selectContactMonthCB.getValue() == null) {
                ObservableList<Appointments> byContact = DBAppointments.getByContact(contactId);
                apptsTableView.setItems(byContact);
                totalContactsLbl.setText("Total Appointments: " + byContact.size());
            } else {
                int month = selectContactMonthCB.getValue().getId();
                ObservableList<Appointments> byContactMonth = DBAppointments.getByContactMonth(contactId, month);
                apptsTableView.setItems(byContactMonth);
                totalContactsLbl.setText("Total Appointments: " + byContactMonth.size());
            }
        }

    }

    /** @param event Clear selections in contacts and month combo boxes. */
    @FXML
    public void onActionSelectClear(ActionEvent event) {
        selectContactCB.getSelectionModel().clearSelection();
        selectContactMonthCB.getSelectionModel().clearSelection();

    }

    /** @param event Populates reports tableview if type and month combo boxes have a selection.
     *  Set total appointments label to number of appointments found filtered by selections.
     */
    @FXML
    public void onSelectCustGo(ActionEvent event) {

        if((reportsTypeCbox.getValue() != null) && (selectApptMonthCB.getValue() != null)) {
            String type = reportsTypeCbox.getValue().getType();
            int month = selectApptMonthCB.getValue().getId();

            ObservableList<Appointments> byTypeMonth = DBAppointments.getByTypeMonth(type, month);
            apptsTableView.setItems(byTypeMonth);

            totalCustomersLbl.setText("Total Appointments: " + byTypeMonth.size());
        }

    }

    /** @param event Resets combo box after a selection is made. */
    @FXML
    public void onActionContactMonthCB(ActionEvent event) {
        onSelectContactsGo(null);
    }

    /** @param event Go back to log in page. */
    @FXML
    public void onActionLogOut(ActionEvent event) throws IOException {
        Helper.logInPage(event);
    }

    /** @param event Resets combo box after a selection is made. */
    @FXML
    public void selectContactReport(ActionEvent event) {
        onSelectContactsGo(null);
    }

    /** @param event Resets combo box after a selection is made. */
    @FXML
    public void selectTypeReports(ActionEvent event) {
        onSelectCustGo(null);

    }

    /** @param url
     * @param resourceBundle
     * Populate both month combo boxes with allMonths observable list.
     * Populate type and contact combo boxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Months> allMonths = FXCollections.observableArrayList(
                new Months(1, "January"),
                new Months(2, "February"),
                new Months(3, "March"),
                new Months(4, "April"),
                new Months(5, "May"),
                new Months(6, "June"),
                new Months(7, "July"),
                new Months(8, "August"),
                new Months(9, "September"),
                new Months(10, "November"),
                new Months(11, "October"),
                new Months(12, "December")
        );

        selectApptMonthCB.setItems(allMonths);
        selectContactMonthCB.setItems(allMonths);

        reportsTypeCbox.setItems(DBAppointments.getDistinctType());

        selectContactCB.setItems(DBContacts.getAllContacts());

        apptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptContactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));


    }
}

