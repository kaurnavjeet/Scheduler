package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBUsers class contains all queries to the Users table in the database.
 */
public class DBUsers {

    /** @return an observable list of all the users in database
     * Makes query of all users to database, connects to database, gets a result set.
     * Loops through the result set and builds new users objects.
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Users U = new Users(id, userName, password);
                allUsers.add(U);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allUsers;
    }

    /** @param userName
     * @return username from database that matches param
     */
    public static String selectUser(String userName) {

        try{
            String sql = "SELECT User_Name from users WHERE User_Name = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getString("User_Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No user found.";

    }

    /** @param password
     * @return password from database that matches param
     */
    public static String selectPassword(String password) {

        try{
            String sql = "SELECT Password from users WHERE Password = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, password);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getString("Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    /** @param userName
     * @param password
     * @return user id from database of the provided username and password combination
     */
    public static int getUserId(String userName, String password) {
        if(selectUser(userName).equals(selectPassword(password))) {
            try{
                String sql = "SELECT User_ID from users WHERE User_Name = ? AND Password = ?";

                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setString(1, userName);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    return rs.getInt("User_ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        return -1;
    }


}
