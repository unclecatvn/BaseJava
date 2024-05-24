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
import util.WebContext;

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

        WebContext.setCurrentRequest(httpRequest);
        WebContext.setCurrentResponse(httpResponse);

        try {
            String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
            if (!path.startsWith("/assets")) {
                Router.route();
            } else {
                chain.doFilter(request, response);
            }
        } finally {
            WebContext.clear();
        }
    }
}
