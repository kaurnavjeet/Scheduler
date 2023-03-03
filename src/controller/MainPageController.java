package controller;

import DBAccess.DBAppointments;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MainPageController provides logic for the main page screen after user logs in.
 */
public class MainPageController implements Initializable {
    Stage stage;
    Scene scene;
    @FXML
    private RadioButton allApptRBttn;

    @FXML
    private TableColumn<?, ?> apptContactId;

    @FXML
    private TableColumn<?, ?> apptCustomerId;

    @FXML
    private TableColumn<?, ?> apptDescription;

    @FXML
    private TableColumn<?, ?> apptEndDate;

    @FXML
    private TableColumn<?, ?> apptEndTime;

    @FXML
    private TableColumn<?, ?> apptId;

    @FXML
    private TableColumn<?, ?> apptLocation;

    @FXML
    private TableColumn<?, ?> apptStartDate;

    @FXML
    private TableColumn<?, ?> apptStartTime;

    @FXML
    private TableColumn<?, ?> apptTitle;

    @FXML
    private TableColumn<?, ?> apptType;

    @FXML
    private TableColumn<?, ?> apptUserId;

    @FXML
    private TableView<Appointments> apptsTableView;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private RadioButton byMonthRBttn;

    @FXML
    private RadioButton byWeekRBttn;

    /** @param event opens add appointment form. */
    @FXML
    public void onActionAddAppt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** @param event selects an appointment to delete.
     * If no appointment is selected, alert user to select one.
     * Alerts user to successful deletion.
     * Resets Tableview with updated appointments.
     * */
    @FXML
    public void onActionDeleteAppt(ActionEvent event) {
        Appointments selectedAppt = apptsTableView.getSelectionModel().getSelectedItem();

        if(selectedAppt == null) {
            Helper.alertError("Please select an appointment to delete.");
        } else {
            DBAppointments.delete(selectedAppt.getId());
            Helper.alertInfo("Appointment: " + selectedAppt.getId() + " of Type: " + selectedAppt.getType() + " has been deleted.");
            apptsTableView.setItems(DBAppointments.getAllAppointments());
        }

    }

    /** @param event opens modify appointment form.
     * If no appointment is selected, alert user to select one.
     * Get data from ModifyAppointmentController sendData method to populate modify appointment form with the selected data.
     * */
    @FXML
    public void onActionModifyAppt(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointment.fxml"));
            Parent root = loader.load();

            ModifyAppointmentController modifyAppointmentController = loader.getController();
            modifyAppointmentController.sendData(apptsTableView.getSelectionModel().getSelectedIndex(), apptsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (NullPointerException exception) {
            Helper.alertError("Please select an appointment to modify.");
        }

    }

    /** @param event opens Customers page. */
    @FXML
    public void onActionSelectCustomers(ActionEvent event) throws IOException {
        Helper.customersPage(event);

    }

    /** @param event go back to log in page. */
    @FXML
    public void onActionSelectLogOut(ActionEvent event) throws IOException {
        Helper.logInPage(event);
    }

    /** @param event opens Reports page. */
    @FXML
    public void onActionSelectReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void selectAllAppts(ActionEvent event) {
    }

    @FXML
    void selectByMonth(ActionEvent event) {
    }

    @FXML
    void selectByWeek(ActionEvent event) {
    }

    /** @param url
     * @param resourceBundle
     * Populate appointments tableview.
     * Three lambda expressions setting radio buttons on Main Page form to event handlers displaying appointments filtered by all, month, or week.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptsTableView.setItems(DBAppointments.getAllAppointments());

        allApptRBttn.setOnAction(e -> {
            apptsTableView.setItems(DBAppointments.getAllAppointments());
        });

        byMonthRBttn.setOnAction(e -> {
            apptsTableView.setItems(DBAppointments.getByMonth());
        });

        byWeekRBttn.setOnAction(e -> {
            apptsTableView.setItems(DBAppointments.getByWeek());
        });



        apptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptContactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));


    }
}
