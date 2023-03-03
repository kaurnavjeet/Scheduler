package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBCountries class contains all queries to the countries table in the database.
 */
public class DBCountries {

    /** @return an observable list of all the countries in database.
     * Makes query of all countries to database, connects to database, gets a result set.
     * Loops through the result set and builds new countries objects.
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Countries C = new Countries(countryId, country);
               allCountries.add(C);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allCountries;
    }
}
