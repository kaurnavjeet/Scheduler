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
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * ModifyCustomersController provides logic for the modify customer screen.
 */
public class ModifyCustomerController implements Initializable {

    @FXML
    private ComboBox<Countries> custCountryCBModify;

    @FXML
    private ComboBox<FirstLevelDivisions> custStateCBModify;

    @FXML
    private TextField customerAddressModify;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField customerNameModify;

    @FXML
    private TextField phoneNumberModify;

    @FXML
    private TextField postalCodeModify;

    private int index = 0;

    /** @param selectedIndex The index of the customer that is selected in Customers Page.
     * @param customer The customer object that is selected in Customers Page to be modified.
     * Send data of the selected customer at the selected index back to CustomersController.
     * Two lambda expressions using forEach to loop through countries and divisions to set values of combo boxes to the values of the selected customer.
     * */
    public void sendData(int selectedIndex, Customers customer) {

        index = selectedIndex;
        customerIdTxt.setText(String.valueOf(customer.getCustomerId()));
        customerNameModify.setText(customer.getCustomerName());
        customerAddressModify.setText(customer.getCustomerAddress());
        postalCodeModify.setText(customer.getPostalCode());
        phoneNumberModify.setText(customer.getPhoneNumber());

        custCountryCBModify.setItems(DBCountries.getAllCountries());

        DBCountries.getAllCountries().forEach(C -> {
            if(C.getCountryId() == customer.getCountryId()) {
                custCountryCBModify.setValue(C);
            }
        });

        custStateCBModify.setItems(DBDivisions.getDivisionNames(customer.getCountryId()));

        DBDivisions.getDivisionNames(customer.getCountryId()).forEach(F -> {
            if(F.getDivisionId() == customer.getDivisionId()) {
                custStateCBModify.setValue(F);
            }
        });
    }

    /** @param event Go back to Customers page. */
    @FXML
    public void onActionDisplayCustomers(ActionEvent event) throws IOException {
        Helper.customersPage(event);
    }

    /** @param event Update customer to database if valid.
     * Validation checks for empty text fields and combo boxes.
     * Update customer to database and display customers page.
     */
    @FXML
    public void onActionUpdateCustomer(ActionEvent event) throws IOException {
        //Input validation for empty
        if(customerNameModify.getText().isEmpty()) {
            Helper.alertError("Name field is blank. Please enter a name.");
        } else if(customerAddressModify.getText().isEmpty()) {
            Helper.alertError("Address field is blank. Please enter address.");
        } else if(postalCodeModify.getText().isEmpty()) {
            Helper.alertError("Postal Code field is blank. Please enter postal code.");
        } else if(phoneNumberModify.getText().isEmpty()) {
            Helper.alertError("Phone Number field is blank. Please enter phone number.");
        } else if(custCountryCBModify.getValue() == null) {
            Helper.alertError("Please select a country.");
        } else if(custStateCBModify.getValue() == null) {
            Helper.alertError("Please select a state.");
        } else {
            //Get data from CB and text fields and update customer to database
            FirstLevelDivisions division = custStateCBModify.getValue();
            int divisionId = division.getDivisionId();
            int customerId = Integer.parseInt(customerIdTxt.getText());
            String customerName = customerNameModify.getText();
            String customerAddress = customerAddressModify.getText();
            String postalCode = postalCodeModify.getText();
            String phoneNumber = phoneNumberModify.getText();

            DBCustomers.update(customerId, customerName, customerAddress, postalCode, phoneNumber, divisionId);
            Helper.customersPage(event);
        }


    }

    /** @param event Populate division combo box according to selection of country combo box.
     */
    @FXML
    public void selectCountryCustModify(ActionEvent event) {
        Countries country = custCountryCBModify.getSelectionModel().getSelectedItem();
        custStateCBModify.setItems(DBDivisions.getDivisionNames(country.getCountryId()));
        custStateCBModify.getSelectionModel().selectFirst();

    }

    @FXML
    void selectStateCustModify(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
