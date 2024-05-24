package router;

import controller.HomeController;

public class RouteConfig {

    public static void setupRoutes() {
        Router.register("GET", "/", HomeController.class, "index");
        // Đăng ký thêm các route khác tại đây

        Router.group("/auth", () -> {
            Router.register("GET", "login", HomeController.class, "index1");
        });
    }
}
