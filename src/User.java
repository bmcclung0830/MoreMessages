import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brittany on 4/18/16.
 */
public class User {
    static String name;
    static String password;
    static ArrayList<Message> messages = new ArrayList<>();

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
