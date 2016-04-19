import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static User user;
    static ArrayList<Message> messages = new ArrayList<>();
    static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {



        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap h = new HashMap();

                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User user = users.get(userName);

                    if (user == null) {
                        return new ModelAndView(h, "index.html");
                    }
                    else{
                        h.put("user", user.name);
                        h.put("messages", messages);
                        return new ModelAndView(h, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("userName");
                    user = users.put(name, new User(name));

                    if (user == null){
                        users.put(name, new User(name));
                    }
                    Session session = request.session();
                    session.attribute("userName", name);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-message",
                ((request, response) -> {
                    String umessage = request.queryParams("userMessage");
                    messages.add(new Message(umessage));
                    response.redirect("/");
                    return "";
                })
        );
    }
}
