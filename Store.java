/**
 * Store
 *
 * Store class holding just the name of the store for sellers to create
 *
 * @author Yudon Shin, lab sec L-24
 * 
 * @version November 13, 2022
 */
public class Store {
    private String name;

    public Store(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
