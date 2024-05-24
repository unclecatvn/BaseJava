package router;

import controller.HomeController;

public class RouteConfig {

    public static void setupRoutes() {
        Router.register("GET", "/", HomeController.class, "index");
        // Đăng ký thêm các route khác tại đây
        // Ví dụ: Router.register("GET", "/about", AboutController.class, "index");
        // Router.register("POST", "/login", AuthController.class, "login");

        Router.group("/auth", () -> {
            Router.register("GET", "login", HomeController.class, "index1");
//            Router.register("POST", "logout", HomeController.class, "logout");
        });
    }
}
