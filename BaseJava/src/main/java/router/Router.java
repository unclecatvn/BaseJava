package router;

import jakarta.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Router {

    // Tạo một map để lưu trữ các route, mỗi route liên kết một chuỗi khóa với một đối tượng Route
    private static final Map<String, Route> routes = new HashMap<>();

    // Phương thức để đăng ký một route mới
    public static void register(String method, String path, Class<?> controller, String methodName) {
        // Lưu trữ route vào map với khóa là phương thức HTTP và đường dẫn
        routes.put(method + " " + path, new Route(controller, methodName));
    }

    // Phương thức xử lý routing cho mỗi request
    public static void route(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath(); // Lấy context path
        String requestURI = request.getRequestURI(); // Lấy URI được yêu cầu
        String pathInfo = requestURI.substring(contextPath.length()); // Loại bỏ context path

        String httpMethod = request.getMethod();
        String key = httpMethod + " " + (pathInfo != null ? pathInfo : "/");
        Route route = routes.get(key);

        if (route != null) {
            try {
                Method actionMethod = route.getController().getMethod(route.getMethodName(), HttpServletRequest.class, HttpServletResponse.class);
                actionMethod.invoke(route.getController().newInstance(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }
}

class Route {

    private Class<?> controller;
    private String methodName;

    // Constructor cho Route, nhận controller và methodName để sau này có thể gọi
    public Route(Class<?> controller, String methodName) {
        this.controller = controller;
        this.methodName = methodName;
    }

    // Getter cho controller
    public Class<?> getController() {
        return controller;
    }

    // Getter cho methodName
    public String getMethodName() {
        return methodName;
    }
}
