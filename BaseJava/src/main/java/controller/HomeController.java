package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController {

    public static void index(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
//            System.out.println("123123");
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void index1(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/layout/home.jsp").forward(req, resp);
//            System.out.println("123123");
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
