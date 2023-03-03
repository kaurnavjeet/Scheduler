package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import helper.Helper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * CustomersController provides logic for the customers screen.
 */
public class CustomersController implements Initializable {
    Stage stage;
    Scene scene;
    @FXML
    private TableColumn<Customers, String> customerAddress;

    @FXML
    private TableColumn<Customers, String> customerCountry;
    @FXML
    private TableColumn<Customers, String> customerState;

    @FXML
    private TableColumn<Customers, Integer> customerId;

    @FXML
    private TableColumn<Customers, String > customerName;

    @FXML
    private TableColumn<Customers, String > customerPhoneNo;

    @FXML
    private TableColumn<Customers, String> customerPostalCode;

    @FXML
    private TableView<Customers> customersTableView;

    /** @param event Opens add customer form. */
    @FXML
    public void onActionAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** @param event Go back to main page. */
    @FXML
    public void onActionBackBttn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /** @param event Selects a customer to delete.
     * If no customer is selected, alert user to select one.
     * If selected customer id has associated appointments, those appointments are deleted first.
     * Deletes customer from database and alerts user to successful deletion.
     * Resets tableview with updated customers.
     * One lambda expression using forEach to loop through filtered appointments to delete associated appointments.
     * */
    @FXML
    public void onActionDeleteCustomer(ActionEvent event) {
        Customers selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null) {
            Helper.alertError("Please select a customer to delete.");
        } else {
            int customerId = selectedCustomer.getCustomerId();
            ObservableList<Appointments> filteredAppts = DBAppointments.getApptByCustomer(customerId);
            if (filteredAppts.size() != 0) {
                filteredAppts.forEach(a -> DBAppointments.delete(a.getId()));
            }

            DBCustomers.delete(selectedCustomer.getCustomerId());
            Helper.alertInfo("Customer and all associated appointments have been deleted.");
            customersTableView.setItems(DBCustomers.getAllCustomers());
        }

    }

    /** @param event Go back to log in page. */
    @FXML
    public void onActionLogOut(ActionEvent event) throws IOException {
        Helper.logInPage(event);
    }

    /** @param event opens modify customer form.
     * If no customer is selected, alert user to select one.
     * Get data from ModifyCustomerController sendData method to populate modify customer form with the selected data.
     * */
    @FXML
    public void onActionModifyCustomer(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyCustomer.fxml"));
            Parent root = loader.load();

            ModifyCustomerController modifyCustomerController = loader.getController();
            modifyCustomerController.sendData(customersTableView.getSelectionModel().getSelectedIndex(), customersTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (NullPointerException exception) {
            Helper.alertError("Please select a customer to modify.");
        }

    }

    /** @param url
     * @param resourceBundle
     * Populate customers tableview.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customers> allCustomers = DBCustomers.getAllCustomers();
        customersTableView.setItems(allCustomers);

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerState.setCellValueFactory(new PropertyValueFactory<>("state"));



    }
}

