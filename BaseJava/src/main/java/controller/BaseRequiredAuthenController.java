/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
//import java.io.PrintWriter;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import model.User;

/**
 *
 * @author Admin
 */
public abstract class BaseRequiredAuthenController extends HttpServlet {

    private boolean isAuthenticated(HttpServletRequest request) {
        // Kiểm tra xem người dùng đã đăng nhập và có quyền hợp lệ hay không
        return request.getSession().getAttribute("user") != null && hasPermission(request);
    }

    private boolean hasPermission(HttpServletRequest request) {
        // Lấy role từ session, đã được lưu khi người dùng đăng nhập
        String userRole = (String) request.getSession().getAttribute("role");
        String roleRequired = getRequiredRoles();
        if (roleRequired == null || roleRequired.isEmpty()) {
            // Nếu không yêu cầu role nào, coi như đã xác thực
            return true;
        }
        String[] roles = roleRequired.split(",");
        for (String role : roles) {
            if (role.trim().equals(userRole)) {
                return true;
            }
        }
        return false;
    }

    protected abstract String getRequiredRoles();

    protected void navigate(HttpServletRequest request, HttpServletResponse response, String viewName) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp");
        dispatcher.forward(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (isAuthenticated(request)) {
            // Cho phép các lớp con xử lý tiếp theo yêu cầu
            if (request.getRequestURI().contains("/login")) {
                // Người dùng đã đăng nhập và cố gắng truy cập trang đăng nhập
                response.sendRedirect("/WEB-INF/views/home.jsp");
            } else {
                // Cho phép các lớp con xử lý tiếp theo yêu cầu
            }
        } else {
            if (request.getRequestURI().contains("/login")) {
                // Người dùng không đăng nhập và truy cập trang đăng nhập bình thường
                // Không làm gì hoặc xử lý riêng cho trang đăng nhập nếu cần
            } else {
                // Người dùng không có quyền truy cập
                request.setAttribute("mess", "Bạn không có quyền truy cập!");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            }
        }
    }
}
