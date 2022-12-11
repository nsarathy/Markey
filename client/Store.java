/**
 * Store
 * <p>
 * Store blueprint
 *
 * @author shin369
 * @version 12/11/2022
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

    @Override
    public String toString() {
        return name;
    }
}
