package middleware;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import router.Middleware;
import util.ViewUtils;

public class AuthenticationMiddleware implements Middleware {

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Kiểm tra xác thực và role
        if (!isAuthenticated(request)) {
            ViewUtils.redirect("/");
            return false;
        }

        // Kiểm tra role
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/home");  // Chuyển hướng nếu không phải admin
            return false;
        }
        System.out.println("123123");

        return true;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        // Xử lý kiểm tra
        return request.getSession().getAttribute("user") != null;
    }

    private boolean isAdmin(HttpServletRequest request) {
        // Lấy role từ session và kiểm tra
        String role = (String) request.getSession().getAttribute("role");
        return "admin".equals(role);
    }
}
