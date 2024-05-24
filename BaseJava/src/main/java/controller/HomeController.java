package controller;

import util.ViewUtils;

public class HomeController {

    public static void index() {
        ViewUtils.put("message", "Welcome to the Home Page!");
        ViewUtils.render("auth/login");
    }
}
