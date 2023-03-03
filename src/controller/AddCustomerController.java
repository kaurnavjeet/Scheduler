package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Countries;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddCustomersController provides logic for the add customer screen.
 */
public class AddCustomerController implements Initializable {
    @FXML
    private ComboBox<Countries> custCountryCB;

    @FXML
    private ComboBox<FirstLevelDivisions> custStateCB;

    @FXML
    private TextField customerAddressTxt;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private TextField postalCodeTxt;

    /** @param event Go back to Customers page. */
    @FXML
    public void onActionDisplayCustomers(ActionEvent event) throws IOException {
        Helper.customersPage(event);
    }

    /** @param event Save customer to database if valid.
     * Validation checks for empty text fields and combo boxes.
     * Insert customer to database and display customers page.
     */
    @FXML
    public void onActionSaveCustomer(ActionEvent event) throws IOException {
        //Input validation for empty
        if(customerNameTxt.getText().isEmpty()) {
            Helper.alertError("Name field is blank. Please enter a name.");
        } else if(customerAddressTxt.getText().isEmpty()) {
            Helper.alertError("Address field is blank. Please enter address.");
        } else if(postalCodeTxt.getText().isEmpty()) {
            Helper.alertError("Postal Code field is blank. Please enter postal code.");
        } else if(phoneNumberTxt.getText().isEmpty()) {
            Helper.alertError("Phone Number field is blank. Please enter phone number.");
        } else if(custCountryCB.getValue() == null) {
            Helper.alertError("Please select a country.");
        } else if(custStateCB.getValue() == null) {
            Helper.alertError("Please select a state.");
        } else {

            FirstLevelDivisions division = custStateCB.getSelectionModel().getSelectedItem();
            int divisionId = division.getDivisionId();
            String customerName = customerNameTxt.getText();
            String customerAddress = customerAddressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phoneNumber = phoneNumberTxt.getText();

            DBCustomers.insert(customerName, customerAddress, postalCode, phoneNumber, divisionId);
            Helper.customersPage(event);
        }

    }

    /** @param event Populate division combo box according to selection of country combo box.
     */
    @FXML
    public void selectCountryCust(ActionEvent event) {
        Countries country = custCountryCB.getSelectionModel().getSelectedItem();
        custStateCB.setItems(DBDivisions.getDivisionNames(country.getCountryId()));
        custStateCB.setPromptText("Choose state");
    }

    @FXML
    void selectStateCust (ActionEvent event) {
    }

    /** @param url
     * @param resourceBundle
     * Populate country combo box
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custCountryCB.setItems(DBCountries.getAllCountries());
        custCountryCB.setPromptText("Choose country");
    }
}
