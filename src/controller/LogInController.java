package controller;

import DBAccess.DBAppointments;
import DBAccess.DBUsers;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointments;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * LogInController provides logic for the log in screen when app launches.
 */
public class LogInController implements Initializable {

    @FXML
    private Label languageLbl;

    @FXML
    private Label languageChangeLbl;

    @FXML
    private Label locationChangeLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label logInLbl;

    @FXML
    private Label passwordLbl;
    @FXML
    private Label usernameLbl;
    @FXML
    private Button exitBttn;

    @FXML
    private Button logInBttn;

    @FXML
    private PasswordField passwordTxtField;

    @FXML
    private TextField userNameTxtField;

    /** @param event exits app. * */
    @FXML
    public void onActionExit(ActionEvent event) {
        Optional< ButtonType> result = Helper.alertConfirmation("Are you sure you want to exit the application?");
        if(result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /** @param event logs in to app after validation checks.
     * Checks for valid username and password.
     * Translate labels and error messages to English or French depending on system default language.
     * Check for upcoming appointment within 15 minutes of successful log in.
     * Output username and timestamp to login_activity.txt after successful or unsuccessful log in.
     */
    @FXML
    public void onActionLogIn(ActionEvent event) throws IOException {

        String userName = userNameTxtField.getText();
        String password = passwordTxtField.getText();

        ResourceBundle rb = ResourceBundle.getBundle("Language", Locale.getDefault());

        //login_activity.txt file creation
        String filename = "login_activity.txt";
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);

        //Input Validation in English and French
        if (userNameTxtField.getText().isEmpty()) {
            if((Locale.getDefault().getLanguage().equals("fr")) || (Locale.getDefault().getLanguage().equals("en"))) {
                Helper.alertError(rb.getString("BlankUsername"));
            }
        } else if (passwordTxtField.getText().isEmpty()) {
            if((Locale.getDefault().getLanguage().equals("fr")) || (Locale.getDefault().getLanguage().equals("en"))) {
                Helper.alertError(rb.getString("BlankPassword"));
            }
        } else {
            //Username and password validation
            if(DBUsers.getUserId(userName, password) > 0) {
                Helper.mainPage(event);
                LocalDateTime now = LocalDateTime.now();

                //Check if appointment within 15 minutes of log in
                boolean found = false;
                for(Appointments a : DBAppointments.getAllAppointments()) {
                    if (a.getStart().isAfter(now) && a.getStart().isBefore(now.plusMinutes(15))) {
                        found = true;
                        Helper.alertInfo("You have an appointment in 15 minutes \n" + "Appointment: " + a.getId() + " at " + a.getStart());
                    }
                }
                if(!found) {
                    Helper.alertInfo("There is no appointment in 15 minutes.");
                }

                outputFile.print("Username: " + userName + " log in attempt at " + Timestamp.valueOf(LocalDateTime.now()) + " was successful.\n");
            } else {
                if((Locale.getDefault().getLanguage().equals("fr")) || (Locale.getDefault().getLanguage().equals("en"))) {
                    Helper.alertError(rb.getString("WrongUNPassword"));
                }

                outputFile.print("Username: " + userName + " log in attempt at " + Timestamp.valueOf(LocalDateTime.now()) + " was unsuccessful.\n");
            }
        }

        outputFile.close();
    }

    /** @param url
     * @param resourceBundle
     * Populate labels in English or French depending on system language.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        String zoneId = ZoneId.systemDefault().getId();

        ResourceBundle rb = ResourceBundle.getBundle("Language", Locale.getDefault());

        if((Locale.getDefault().getLanguage().equals("fr")) || (Locale.getDefault().getLanguage().equals("en"))) {
                logInLbl.setText(rb.getString("LogIn"));
                usernameLbl.setText(rb.getString("Username"));
                passwordLbl.setText(rb.getString("Password"));
                languageLbl.setText(rb.getString("LanguageLabel"));
                languageChangeLbl.setText(rb.getString("Language"));
                locationLbl.setText(rb.getString("Location"));
                locationChangeLbl.setText(zoneId);
                logInBttn.setText(rb.getString("LogIn"));
                exitBttn.setText(rb.getString("Exit"));

        }

    }

}
