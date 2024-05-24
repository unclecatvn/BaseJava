package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import util.ViewUtils;

public class HomeController {

    public static void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/index.jsp").forward(req, resp);
        ViewUtils.put("message", "Welcome to the Home Page!");
        ViewUtils.render(req, resp, "auth/login");
    }

    public static void index1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
