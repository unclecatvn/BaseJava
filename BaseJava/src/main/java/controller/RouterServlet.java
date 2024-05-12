package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import router.Router;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class RouterServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RouterServlet.class.getName());

    @Override
    public void init() {
        Router.get("/home", (req, res) -> {
            try {
                new HomeController().index(req, res);
            } catch (ServletException ex) {
                Logger.getLogger(RouterServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RouterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Router.get("/", (req, res) -> {
            try {
                res.sendRedirect(req.getContextPath() + "/home");
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error redirecting", e);
            }
        });
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) {
        Router.resolve(req, res, req.getMethod(), req.getContextPath());
    }
}
