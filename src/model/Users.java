package model;

/**
 * Users class provides users objects
 */
public class Users {
    private int userId;
    private String userName;
    private String password;

    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public Users() {

    }

    /** @return user id */
    public int getUserId() {
        return userId;
    }

    /** @return username */
    public String getUserName() {
        return userName;
    }

    /** @param userName set username */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** @return user password */
    public String getPassword() {
        return password;
    }

    /** @param userId set user id */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** @param password set user password */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @return username
     * override toString() method
     */
    public String toString() {
        return (userName);
    }

}
