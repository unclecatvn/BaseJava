package router;

import controller.HomeController;
import middleware.AuthenticationMiddleware;

public class RouteConfig {

    public static void setupRoutes() {
        Router.register("GET", "/", HomeController.class, "index");
        // Đăng ký thêm các route khác tại đây

//        Router.group("/auth", () -> {
//            Router.register("GET", "login", HomeController.class, "index");
//        });
        Router.group("/auth", () -> {
            Router.register("GET", "login", HomeController.class, "index");
        }).addMiddleware(new AuthenticationMiddleware()).applyMiddlewares();

        Router.group("/auth", () -> {
            Router.register("GET", "test", HomeController.class, "test");
        });
    }
}
