package router;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Router {

    private static final Logger LOGGER = Logger.getLogger(Router.class.getName());
    private static final Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> getRoutes = new ConcurrentHashMap<>();
    private static final Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> postRoutes = new ConcurrentHashMap<>();

    public static void get(String path, BiConsumer<HttpServletRequest, HttpServletResponse> handler) {
        getRoutes.put(normalizePath(path), handler);
    }

    public static void post(String path, BiConsumer<HttpServletRequest, HttpServletResponse> handler) {
        postRoutes.put(normalizePath(path), handler);
    }

    private static String normalizePath(String path) {
        return path.endsWith("/") ? path : path + "/";
    }

    public static void resolve(HttpServletRequest request, HttpServletResponse response, String methodType, String contextPath) {
        String path = normalizePath(request.getRequestURI().substring(contextPath.length()));
        Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> routes = "GET".equals(methodType) ? getRoutes : postRoutes;

        BiConsumer<HttpServletRequest, HttpServletResponse> handler = routes.getOrDefault(path, Router::sendNotFound);
        try {
            handler.accept(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private static void sendNotFound(HttpServletRequest request, HttpServletResponse response) {
        sendError(response, HttpServletResponse.SC_NOT_FOUND, "The requested resource was not found.");
    }

    private static void sendError(HttpServletResponse response, int statusCode, String message) {
        try {
            response.sendError(statusCode, message);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to send error response", e);
        }
    }
}
