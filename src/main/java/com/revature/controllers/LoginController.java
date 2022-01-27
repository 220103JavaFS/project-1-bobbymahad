package com.revature.controllers;

import com.revature.models.users.FinanceManager;
import com.revature.models.users.NonFinanceManager;
import com.revature.models.users.User;
import com.revature.services.LoginService;
import com.revature.util.LoginAttempt;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController extends Controller {

    private LoginService loginService = new LoginService();

    Handler login = (ctx) -> {
        //only allow for login if no user is currently logged in, otherwise prompt the user to logout
        if (ctx.req.getSession(false) != null) {
            ctx.status(401); //not authorized until logout
            ctx.result("Already logged in, please log out to log in with another user.");
        }
        else {
            LoginAttempt log = ctx.bodyAsClass(LoginAttempt.class); //JS logic ensures that username and password aren't blank
            System.out.println(log.getUsername());
            //User user = loginService.loginUser(log);

            User user = null;
            if (user != null) {
                //either the username wasn't in the database or the password was incorrect
                ctx.result("Sorry, either the username or password was incorrect. Please retype and then try again.");
                ctx.status(400);
            }
            else {
                //TODO: This is just for JSON testing, delete these lines later
                User basic = new User(1, "rfloyd01", "helloW0rld", "Robert", "Floyd", "robert.floyd@company.com");
                User nfManager = new NonFinanceManager(2, "Manage1", "helloW0rld", "Matt", "Damon", "matthew.damon@company.com");
                User fManager = new FinanceManager(3, "Manage2", "helloW0rld", "Ben", "Affleck", "ben.affleck@company.com");

                if (log.getUsername().equals("rfloyd01")) {
                    ctx.json(basic);
                    ctx.status(200);
                }
                else if (log.getUsername().equals("Manage1")) {
                    ctx.json(nfManager);
                    ctx.status(200);
                }
                else if (log.getUsername().equals("Manage2")) {
                    System.out.println("yoooo");
                    ctx.json(fManager);
                    ctx.status(200);
                }
                else {
                    ctx.status(400);
                }
            }
        }
    };

    Handler logout = (ctx) -> {
        //we can only access the logout page if we're actually logged in
        if (ctx.req.getSession(false) != null) {
            //log the current user out
            ctx.req.getSession().invalidate();
            ctx.status(202); //return 202 Accepted code
        }
        else ctx.status(401); //no one is logged in, restrict access the logout page
    };

    @Override
    public void addRoutes(Javalin app) {
        app.post("/login", login);
        app.delete("/logout", logout); //felt it was easier to include this in the login controller instead of creating separate logout controller
    }
}
