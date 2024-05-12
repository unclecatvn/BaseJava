/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Hoai Nam
 */
public class HomeController {

//    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        RequestDispatcher dispatcher = request.getRequestDispatcher("layout/home.jsp");
////        dispatcher.forward(request, response);
//        return "Web Service data successfuly consumed";
//    }
    
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().write("Web Service data successfully consumed12123123");
    }
}
