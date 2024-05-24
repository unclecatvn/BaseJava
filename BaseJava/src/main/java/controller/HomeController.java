package controller;

import util.ViewUtils;

public class HomeController {

    public static void index() {
        ViewUtils.put("message", "Welcome to the Home Page!");
        ViewUtils.render("auth/login");
    }

    public static void test() {
        ViewUtils.put("message", "Welcome to the Home Page!");
        ViewUtils.render("test/test");
    }
    
    public static void test1() {
        ViewUtils.put("message", "Welcome to the Home Page!");
        ViewUtils.render("test/test1");
    }
}
