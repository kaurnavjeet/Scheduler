package DBAccess;

import helper.Helper;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * DBAppointments class contains all queries to the appointments table in the database.
 */
public class DBAppointments {

    /** @return an observable list of all the appointments in database.
     * Makes query of all appointments to database, connects to database, gets a result set.
     * Loops through the result set and builds new appointments objects.
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> allAppts = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();

                Appointments A = new Appointments(id, title, description, location, contactId, type, start, end, customerId, userId);
                allAppts.add(A);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allAppts;
    }

    /** @param customerId filter appointments table by a specific customer id and return an observable list of the result.
     * @return an observable list of all the appointments filtered by a customer id in database.
     */
    public static ObservableList<Appointments> getApptByCustomer(int customerId) {
        ObservableList<Appointments> apptsByCustomer = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments WHERE appointments.Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();

                Appointments A = new Appointments(id, title, description, location, contactId, type, start, end, custId, userId);
                apptsByCustomer.add(A);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return apptsByCustomer;
    }

    /** @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param contactId
     * @param userId
     *  @return integer greater than 0 if insert was successful to database.
     * Adds new appointment to database with given params.
     */
    public static int insert(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int contactId, int userId) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, "script");
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10, "script");
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                Helper.alertInfo("Appointment added successfully");
            } else {
                Helper.alertError("Appointment not added. Please try again");
            }
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }

    /** @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param contactId
     * @param userId
     * @param apptId
     * Updates appointment to database at specific appointment id with given params.
     */
    public static void update(int apptId, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int contactId, int userId) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                    "WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(8, customerId);
            ps.setInt(9, userId);
            ps.setInt(10, contactId);
            ps.setInt(11, apptId);

            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /** @param apptId deletes appointment from database with given appointment id.
     */
    public static int delete(int apptId) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, apptId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /** @return an observable list of all the appointments filtered by current month in database.
     */
    public static ObservableList<Appointments> getByMonth() {
        ObservableList<Appointments> byMonth = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(NOW())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();

                Appointments A = new Appointments(id, title, description, location, contactId, type, start, end, customerId, userId);
                byMonth.add(A);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return byMonth;
    }

    /** @return an observable list of all the appointments filtered by current week in database.
     */
    public static ObservableList<Appointments> getByWeek() {
        ObservableList<Appointments> byWeek = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE YEARWEEK(Start) = YEARWEEK(NOW())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();

                Appointments A = new Appointments(id, title, description, location, contactId, type, start, end, customerId, userId);
                byWeek.add(A);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return byWeek;
    }

    /** @return an observable list of all the appointments filtered by distinct type in database.
     */
    public static ObservableList<Appointments> getDistinctType() {
        ObservableList<Appointments> distinctType = FXCollections.observableArrayList();

        try {
            String sql = "WITH NODUPE AS " +
                    "(SELECT *, ROW_NUMBER() OVER (PARTITION BY Type ORDER BY Type) AS OCCURENCE FROM appointments) " +
                    "SELECT * FROM NODUPE WHERE OCCURENCE = 1";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();

                Appointments A = new Appointments(id, title, description, location, contactId, type, start, end, customerId, userId);
                distinctType.add(A);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distinctType;
    }

    /** @param selectedType
     * @param month
     *  @return an observable list of all the appointments filtered by type and month of current year in database.
     */
    public static ObservableList<Appointments> getByTypeMonth(String selectedType, int month) {
        ObservableList<Appointments> byTypeMonth = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Type = ? AND MONTH(Start) = ? AND YEAR(Start) = YEAR(NOW())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, selectedType);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();

                Appointments A = new Appointments(id, title, description, location, contactId, type, start, end, customerId, userId);
                byTypeMonth.add(A);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return byTypeMonth;
    }

    /** @return an observable list of all the appointments filtered by contact id in database.
     */
    public static ObservableList<Appointments> getByContact(int contactId) {
        ObservableList<Appointments> byContact = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();

                Appointments A = new Appointments(id, title, description, location, contactID, type, start, end, customerId, userId);
                byContact.add(A);

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return byContact;
    }

    /** @param contactId
     * @param month
     * @return an observable list of all the appointments filtered by contact id and month of current year in database.
     */
    public static ObservableList<Appointments> getByContactMonth(int contactId, int month) {
        ObservableList<Appointments> byContactMonth = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = ? AND MONTH(Start) = ? AND YEAR(Start) = YEAR(NOW())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactId);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();

                Appointments A = new Appointments(id, title, description, location, contactID, type, start, end, customerId, userId);
                byContactMonth.add(A);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return byContactMonth;
    }

}

