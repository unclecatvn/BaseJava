package controller;

import util.ViewUtils;

public class HomeController {

    public static void index() {
        ViewUtils.put("message", "Welcome to the Home Page!");
        ViewUtils.render("auth/login");
    }

//    public static void index1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/index.jsp").forward(req, resp);
//    }
}
