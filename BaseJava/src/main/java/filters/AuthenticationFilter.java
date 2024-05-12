/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Hoai Nam
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Cấu hình khởi tạo filter nếu cần
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Lấy URI từ request
        String uri = request.getRequestURI();
        System.out.println(uri);

        // Kiểm tra nếu URI kết thúc bằng ".jsp" và không phải là trang cho phép truy cập trực tiếp
        if (uri.endsWith(".jsp")) {
            // Chuyển hướng người dùng về trang chủ hoặc trang lỗi tùy vào thiết kế ứng dụng của bạn
            response.sendRedirect(request.getContextPath() + "/home.jsp");
            return;
        }

        // Đưa request tiếp theo cho các filter hoặc servlet tiếp theo trong chuỗi
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Dọn dẹp khi filter bị hủy bỏ nếu cần
    }

    // Phương thức kiểm tra các trang .jsp được phép truy cập trực tiếp
//    private boolean isDirectAccessAllowed(String uri) {
//        // Định nghĩa các trang được phép truy cập trực tiếp
//        return uri.endsWith("login.jsp");
//    }
}
