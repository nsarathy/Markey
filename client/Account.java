public class Account {
    /**
     * Account
     * <p>
     * User's account blueprint
     *
     * @author shin369
     * @version 12/11/2022
     */
    private String username;
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return this.username + ";" + this.password;
    }
}