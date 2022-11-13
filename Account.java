/**
 * Account 
 * 
 * Program with basic methods to get usernames and passwords of each user in the account, extended by customer and seller
 * 
 * 
 * @author Yudon Shin, lab sec L-24
 * 
 * @version November 13, 2022
 */
public class Account {
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
