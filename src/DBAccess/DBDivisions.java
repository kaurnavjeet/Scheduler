package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBDivisions class contains all queries to the First_Level_Divisions table in the database.
 */
public class DBDivisions {

    /** @return an observable list of all the first level divisions in database
     * Makes query of all first level divisions to database, connects to database, gets a result set.
     * Loops through the result set and builds new first level division objects.
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                FirstLevelDivisions F = new FirstLevelDivisions(divisionId, division, countryId);
                allDivisions.add(F);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allDivisions;
    }

    /** @param countryId
     * @return a sorted observable list of all the divisions in database with specific country id
     */
    public static ObservableList<FirstLevelDivisions> getDivisionNames(int countryId) {
        ObservableList<FirstLevelDivisions> divisionNames = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions WHERE first_level_divisions.Country_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                FirstLevelDivisions F = new FirstLevelDivisions(divisionId, division, countryID);
                divisionNames.add(F);

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionNames.sorted();
    }

}
