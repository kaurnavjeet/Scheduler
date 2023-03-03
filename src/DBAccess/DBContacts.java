package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBContacts class contains all queries to the contacts table in the database.
 */
public class DBContacts {

    /** @return an observable list of all the contacts in database.
     * Makes query of all contacts to database, connects to database, gets a result set.
     * Loops through the result set and builds new contacts objects.
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");

                Contacts C = new Contacts(id, name);
                allContacts.add(C);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allContacts;
    }
}
