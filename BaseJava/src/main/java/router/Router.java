package router;

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
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        // Loại bỏ context path để lấy đường dẫn thực tế
        String pathInfo = requestURI.substring(contextPath.length());

        String httpMethod = request.getMethod();
        // Tạo khóa để tìm route tương ứng
        String key = httpMethod + " " + (pathInfo != null ? pathInfo : "/");
        Route route = routes.get(key);

        // In ra thông tin route và khóa để debug
//        System.out.println("Route: " + route);
//        System.out.println("Key used for lookup: " + key);

        // Kiểm tra xem có route tương ứng không
        if (route != null) {
            try {
                // Lấy phương thức từ controller tương ứng với route
                Method actionMethod = route.getController().getMethod(route.getMethodName(), HttpServletRequest.class, HttpServletResponse.class);
                // Gọi phương thức đó với thể hiện mới của controller
                actionMethod.invoke(route.getController().newInstance(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            }
        } else {
            // Nếu không tìm thấy route, trả về lỗi 404
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
