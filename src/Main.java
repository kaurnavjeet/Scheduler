import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <p><b>
 * This class creates a Scheduler app with functionality to CRUD appointments, customers, and reports.
 * </b></p>
 * <p>
 * JavaDoc folder found in separate zip file labeled JavaDoc.
 * </p>
 */
public class Main extends Application {

    /**
     * Start method initializes fxml file LogIn.fxml.
     * @param stage root Stage object
     * */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method opens and closes connection to database and launches app.
     * Uncomment Locale inline code to set system default language to French for testing purposes.
     * @param args
     * */
    public static void main(String[] args) {
        JDBC.openConnection();
//        Locale.setDefault(new Locale("fr"));
        launch();
        JDBC.closeConnection();
    }
}