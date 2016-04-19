import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    User user;
    static HashMap<String,User> users = new HashMap<>();

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
                        h.put("messages", user.messages);
                        return new ModelAndView(h, "messages.html");
                    }

                }),
                new MustacheTemplateEngine()

        );

        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("userName");
                    String password = request.queryParams("password");
                    User user = new User(name, password);

                    users.put(name, user);

                    Session session = request.session();
                    session.attribute("userName", name);
                    session.attribute("password", password);

                    if (!user.equals(null)){
                        users.put(name, new User(name, password));
                    }
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-message",
                ((request, response) -> {
                    String umessage = request.queryParams("userMessage");
                    User.messages.add(new Message(umessage));
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/delete-message",
                ((request, response) -> {
                    Integer message = Integer.parseInt(request.queryParams("delete"))-1;
                    User.messages.remove((int) message);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/edit-message",
                ((request, response) -> {
                    Integer message = Integer.parseInt(request.queryParams("number")) -1;
                    String newMessage = request.queryParams("edit");
                    User.messages.set(message, new Message(newMessage));

                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );
        //TODO logout
    }
}
