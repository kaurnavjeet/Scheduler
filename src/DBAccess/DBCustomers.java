package DBAccess;

import helper.Helper;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * DBCustomers class contains all queries to the customers table in the database.
 */
public class DBCustomers {

    /** @return an observable list of all the customers in database
     * Makes query to first_level_divisions and countries table to get state and country of each customer
     * Makes query of all customers to database, connects to database, gets a result set.
     * Loops through the result set and builds new customers objects.
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM customers, first_level_divisions, countries " +
                    "WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID " +
                    "ORDER BY customers.Customer_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String state = rs.getString("Division");
                String country = rs.getString("Country");
                int countryId = rs.getInt("Country_ID");
                Customers C = new Customers(id, name, address, postalCode, phoneNumber, divisionId, state, country, countryId);
                allCustomers.add(C);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allCustomers;
    }

    /** @param countryName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     *  @return integer greater than 0 if insert was successful to database.
     * Adds new customer to database with given params.
     */
    public static int insert(String countryName, String address, String postalCode, String phoneNumber, int divisionId) {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, countryName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divisionId);
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, "script");
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, "script");

           int rowsAffected = ps.executeUpdate();
           if(rowsAffected > 0) {
               Helper.alertInfo("Customer added successfully");
           } else {
               Helper.alertError("Customer not added. Please try again");
           }
           return rowsAffected;

        } catch (SQLException e) {
           e.printStackTrace();
        }
        return -1;

    }

    /** @param countryName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     * @param customerId
     *  @return integer greater than 0 if update was successful to database.
     * Updates customer to database at specific customer id with given params.
     */
    public static int update(int customerId, String countryName, String address, String postalCode, String phoneNumber, int divisionId) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ? , Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? , Last_Update= ? " +
                    "WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, countryName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divisionId);
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(7, customerId);


            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                Helper.alertInfo("Customer updated successfully");
            } else {
                Helper.alertError("Customer not updated. Please try again");
            }
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /** @param customerId deletes customer from database with given customer id.
     */
    public static void delete(int customerId) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);

           ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
