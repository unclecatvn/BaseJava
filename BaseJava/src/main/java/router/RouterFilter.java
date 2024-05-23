package router;

import controller.HomeController;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// WebFilter annotation chỉ định filter này áp dụng cho mọi URL pattern
@WebFilter("/*")
public class RouterFilter implements Filter {

    // Phương thức init được gọi một lần khi filter được khởi tạo
    public void init(FilterConfig filterConfig) {
        // Đăng ký các route cho ứng dụng, mỗi route liên kết một URL tới một phương thức trong controller
//        Router.register("GET", "/home", HomeController.class, "index");
        RouteConfig.setupRoutes();
        // Có thể đăng ký thêm các route khác tại đây
    }

    // Phương thức doFilter xử lý mọi request và response đi qua filter
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Lấy context path của ứng dụng (phần URL trước servlet path)
        String contextPath = httpRequest.getContextPath();
        // Lấy URI được yêu cầu hoàn chỉnh
        String requestURI = httpRequest.getRequestURI();
        // Xác định đường dẫn thực tế được yêu cầu, bỏ qua context path
        String path = requestURI.substring(contextPath.length());

        // Kiểm tra nếu đường dẫn không bắt đầu bằng "/assets" (thường là các tài nguyên tĩnh)
        if (!path.startsWith("/assets")) {
            // Nếu không phải tài nguyên tĩnh, xử lý đường dẫn bằng cách sử dụng router đã định nghĩa
            Router.route(httpRequest, httpResponse);
        } else {
            // Nếu là tài nguyên tĩnh, cho phép request tiếp tục đi qua các filter tiếp theo (nếu có)
            chain.doFilter(request, response);
        }
    }
}
