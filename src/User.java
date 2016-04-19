import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brittany on 4/18/16.
 */
public class User {
    String name;
    String password;
    static ArrayList<Message> messages = new ArrayList<>();

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
}
