package util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewUtils {

    private static ThreadLocal<Map<String, Object>> modelHolder = ThreadLocal.withInitial(HashMap::new);

    public static void put(String key, Object value) {
        modelHolder.get().put(key, value);
    }

    public static void render(HttpServletRequest request, HttpServletResponse response, String viewName) throws ServletException, IOException {
        Map<String, Object> model = modelHolder.get();
        if (model != null) {
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp").forward(request, response);
        modelHolder.remove(); // Clear the model after rendering to prevent leak
    }
}
