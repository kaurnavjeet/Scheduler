package helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.util.Optional;

/**
 * Helper class with helper methods to keep code DRY as much as possible.
 */
public class Helper {

    static Stage stage;
    static Scene scene;


    /** @param errorMessage Alerts an error with the supplied error message. */
    public static void alertError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        Optional<ButtonType> result = alert.showAndWait();
    }

    /** @param infoMessage Alerts an info message. */
    public static void alertInfo(String infoMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, infoMessage);
        Optional<ButtonType> result = alert.showAndWait();
    }

    /** @param confirmMessage Alerts a confirmation with a confirmation message. */
    public static Optional<ButtonType> alertConfirmation(String confirmMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmMessage);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    /** @param event Takes user to the main page. */
    public static void mainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Helper.class.getResource("/view/MainPage.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** @param event Takes user to the login page. */
    public static void logInPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Helper.class.getResource("/view/LogIn.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** @param event Takes user to the customers page. */
    public static void customersPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Helper.class.getResource("/view/Customers.fxml"));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** @param date Takes local date.
     *  @return localDateTime of ZonedDateTime
     *  Converts date and 8:00am EST to system default zone.
     */
    public static ZonedDateTime convertStartTimeZone(LocalDate date) {
        LocalTime startEstTime = LocalTime.of(8, 0);
        LocalDateTime ldt = LocalDateTime.of(date, startEstTime);
        ZonedDateTime startEstDateTime = ldt.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localDateTime = startEstDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localDateTime;
    }

    /** @param date Takes local date.
     *  @return localDateTime of ZonedDateTime
     *  Converts date and 10:00pm EST to system default zone.
     */
    public static ZonedDateTime convertEndTimeZone(LocalDate date) {
        LocalTime startEstTime = LocalTime.of(21, 30);
        LocalDateTime ldt = LocalDateTime.of(date, startEstTime);
        ZonedDateTime startEstDateTime = ldt.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localDateTime = startEstDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localDateTime;
    }


}
