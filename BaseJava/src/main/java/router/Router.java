package router;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.WebContext;

public class Router {

    private static final Map<String, Route> routes = new HashMap<>();
    private static String currentPrefix = "";
    private static Class<?> currentController = null;
    private static List<Middleware> currentMiddlewares = new ArrayList<>();

    public static void register(String method, String path, Class<?> controller, String methodName) {
        String fullPath = currentPrefix.isEmpty() ? path : currentPrefix + "/" + path;
        Route route = new Route(controller != null ? controller : currentController, methodName, new ArrayList<>(currentMiddlewares));
        routes.put(method + " " + fullPath, route);
    }

    public static RouteGroup group(String prefix, Runnable routeDefinitions) {
        String previousPrefix = currentPrefix;
        List<Middleware> previousMiddlewares = new ArrayList<>(currentMiddlewares);
        currentPrefix = prefix;
        currentMiddlewares = new ArrayList<>();  // Reset middlewares for this group
        routeDefinitions.run();
        RouteGroup group = new RouteGroup(prefix, new ArrayList<>(currentMiddlewares));  // Create a new group with prefix
        currentPrefix = previousPrefix;
        currentMiddlewares = previousMiddlewares;
        return group;
    }

    public static class RouteGroup {

        private String prefix;
        private List<Middleware> middlewares;

        public RouteGroup(String prefix, List<Middleware> middlewares) {
            this.prefix = prefix;
            this.middlewares = middlewares;
        }

        public RouteGroup addMiddleware(Middleware middleware) {
            middlewares.add(middleware);
            return this;
        }

        public void applyMiddlewares() {
            for (String key : routes.keySet()) {
                if (key.startsWith("GET " + prefix) || key.startsWith("POST " + prefix)) {  // Only apply to routes within the current group prefix
                    Route route = routes.get(key);
                    for (Middleware middleware : middlewares) {
                        route.addMiddleware(middleware);
                    }
                }
            }
        }
    }

    public static void controller(Class<?> controller, Runnable routeDefinitions) {
        Class<?> previousController = currentController;
        currentController = controller;  // Update currentController
        routeDefinitions.run();  // Run the block defining routes
        currentController = previousController;  // Restore previous controller
    }

    public static void addMiddleware(Middleware middleware) {
        currentMiddlewares.add(middleware);
    }

    public static void route() throws IOException {
        HttpServletRequest request = WebContext.getCurrentRequest();
        HttpServletResponse response = WebContext.getCurrentResponse();
        String key = getRequestKey(request);
        Route route = routes.get(key);

        if (route != null) {
            for (Middleware middleware : route.getMiddlewares()) {
                try {
                    if (!middleware.handle(request, response)) {
                        return; // Stop processing if middleware does not allow to proceed
                    }
                } catch (ServletException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, "Middleware handling failed", ex);
                }
            }
            invokeControllerMethod(route, request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }

    private static String getRequestKey(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        String pathInfo = requestURI.substring(contextPath.length());
        String httpMethod = request.getMethod();
        return httpMethod + " " + (pathInfo != null ? pathInfo : "/");
    }

    private static void invokeControllerMethod(Route route, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Method actionMethod = route.getController().getMethod(route.getMethodName());
            Object controllerInstance = route.getController().getDeclaredConstructor().newInstance();  // Use a suitable constructor
            actionMethod.invoke(controllerInstance);
        } catch (NoSuchMethodException nsme) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, "Method not found: " + route.getMethodName(), nsme);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Method not found: " + route.getMethodName());
        } catch (Exception e) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, "Error invoking controller method", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}

class Route {

    private Class<?> controller;
    private String methodName;
    private List<Middleware> middlewares;

    public Route(Class<?> controller, String methodName, List<Middleware> middlewares) {
        this.controller = controller;
        this.methodName = methodName;
        this.middlewares = new ArrayList<>(middlewares); // Copy constructor để tránh tham chiếu trực tiếp
    }

    public void addMiddleware(Middleware middleware) {
        middlewares.add(middleware);
    }

    public List<Middleware> getMiddlewares() {
        return middlewares;
    }

    public Class<?> getController() {
        return controller;
    }

    public String getMethodName() {
        return methodName;
    }
}
