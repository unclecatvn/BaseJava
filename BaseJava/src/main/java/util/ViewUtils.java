package util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import util.WebContext;

public class ViewUtils {

    public static void put(String key, Object value) {
        HttpServletRequest request = WebContext.getCurrentRequest();
        request.setAttribute(key, value);
    }

    public static void render(String viewName) {
        try {
            HttpServletRequest request = WebContext.getCurrentRequest();
            HttpServletResponse response = WebContext.getCurrentResponse();
            request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            ExceptionHandler.handleException(WebContext.getCurrentRequest(), WebContext.getCurrentResponse(), e);
        }
    }

    public static void redirect(String path) {
        try {
            HttpServletRequest request = WebContext.getCurrentRequest();
            HttpServletResponse response = WebContext.getCurrentResponse();
            response.sendRedirect(request.getContextPath() + path);
        } catch (IOException e) {
            ExceptionHandler.handleException(WebContext.getCurrentRequest(), WebContext.getCurrentResponse(), e);
        }
    }

    public static void sendError(int statusCode, String message) {
        try {
            HttpServletResponse response = WebContext.getCurrentResponse();
            response.sendError(statusCode, message);
        } catch (IOException e) {
            ExceptionHandler.handleException(WebContext.getCurrentRequest(), WebContext.getCurrentResponse(), e);
        }
    }
}
