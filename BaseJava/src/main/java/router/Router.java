package router;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import util.WebContext;

public class Router {

    private static final Map<String, Route> routes = new HashMap<>();
    private static String currentPrefix = "";
    private static Class<?> currentController = null;

    public static void register(String method, String path, Class<?> controller, String methodName) {
        // Nếu currentPrefix không rỗng, nối nó với path
        String fullPath = currentPrefix.isEmpty() ? path : currentPrefix + "/" + path;
        routes.put(method + " " + fullPath, new Route(controller != null ? controller : currentController, methodName));
    }

    public static void group(String prefix, Runnable routeDefinitions) {
        String previousPrefix = currentPrefix;
        currentPrefix = prefix;  // Cập nhật currentPrefix
        routeDefinitions.run();  // Chạy block định nghĩa route
        currentPrefix = previousPrefix;  // Khôi phục prefix trước đó
    }

    public static void controller(Class<?> controller, Runnable routeDefinitions) {
        Class<?> previousController = currentController;
        currentController = controller;  // Cập nhật currentController
        routeDefinitions.run();  // Chạy block định nghĩa route
        currentController = previousController;  // Khôi phục controller trước đó
    }

    public static void route() throws IOException {
        HttpServletRequest request = WebContext.getCurrentRequest();
        HttpServletResponse response = WebContext.getCurrentResponse();
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        String pathInfo = requestURI.substring(contextPath.length());
        String httpMethod = request.getMethod();
        String key = httpMethod + " " + (pathInfo != null ? pathInfo : "/");
        Route route = routes.get(key);

        if (route != null) {
            try {
                Method actionMethod = route.getController().getMethod(route.getMethodName());
                actionMethod.invoke(route.getController().newInstance());
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

    public Route(Class<?> controller, String methodName) {
        this.controller = controller;
        this.methodName = methodName;
    }

    public Class<?> getController() {
        return controller;
    }

    public String getMethodName() {
        return methodName;
    }
}
