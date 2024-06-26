# Mã nguồn JAVA WEB theo mô hình MVC design pattern

## 1. Mô tả:
- Hệ thống được xây dựng sẽ là hệ thống mang lại sự dễ dàng cho việc sử dụng, bảo mật trong việc quản lý và hoàn toàn có thể nâng cấp, sửa đổi về sau này.
- Hệ thống mang lại đầy đủ chức năng cơ bản, có thể áp dụng toàn bộ các dự án.
- Sử dụng mô hình MVC design pattern, có thể tái sử dụng code.

## 2. Công nghệ:

-   Ngôn ngữ lập trình Java JDK 8.
-   Phần mềm để lập trình: Apache Netbeans IDE 13.
-   Server: Apache Tomcat 10.0.
-   Mô hình MVC design pattern JSP và Servlet, Maven.
-   Dữ liệu được lưu trữ bởi hệ quản trị cơ sở dữ liệu MySQL hoặc SQL SERVER.

## 3. Sự khác biệt

- Có route riêng, dễ dàng xử lí controller
- Có middlaware áp dụng vào route riêng, dễ dàng phân quyền
- Có query builder, dễ dàng tái sử dụng được các câu lệnh query từ database (Chưa xử lí)

## 4. Hướng dẫn cơ bản

#### 1. Cách sử dụng cơ sở dữ liệu

- ```db.properties``` trong ```src/resources/db.properties```: thay đổi cơ sở dữ liệu ```DB_CONNECTION=sqlserver``` hoặc ```DB_CONNECTION=mysql``` một cách dễ dàng.

#### 2. Cách sử dụng router riêng của controller

Tạo java class ở controller để sử dụng (Ở đây là HomeController là java class), sử dụng RouteConfig để trỏ về controller và đường dẫn, dưới đây là ví dụ:
```
package router;

import controller.HomeController;
import middleware.AuthenticationMiddleware;

public class RouteConfig {

    public static void setupRoutes() {
        Router.register("GET", "/", HomeController.class, "index");
        // Đăng ký thêm các route khác tại đây
        
        Router.group("/auth", () -> {
            Router.register("GET", "login", HomeController.class, "index");
        }).addMiddleware(new AuthenticationMiddleware()).applyMiddlewares();

        Router.group("/auth", () -> {
            Router.register("GET", "test", HomeController.class, "test");
        });
    }
}

```

Homecontroller chỉ cần khai báo function và gọi tới view như này:
```
package controller;

import util.ViewUtils;

public class HomeController {

    public static void index() {
        ViewUtils.put("message", "Welcome to the Home Page!");
        ViewUtils.render("auth/login");
    }
}

```

#### 3. Cách sử dụng middleware (phân quyền)
- Dưới đây là ví dụ AuthenticationMiddleware.java
```
package middleware;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import router.Middleware;
import util.ViewUtils;

public class AuthenticationMiddleware implements Middleware {

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Kiểm tra xác thực và role
        if (!isAuthenticated(request)) {
            ViewUtils.redirect("/auth/test");
            return false;
        }

        // Kiểm tra role
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/home");  // Chuyển hướng nếu không phải admin
            return false;
        }
        System.out.println("123123");

        return true;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        // Xử lý kiểm tra
        return request.getSession().getAttribute("user") != null;
    }

    private boolean isAdmin(HttpServletRequest request) {
        // Lấy role từ session và kiểm tra
        String role = (String) request.getSession().getAttribute("role");
        return "admin".equals(role);
    }
}

```
- Bổ sung thêm ``` .addMiddleware(new Middleware()).applyMiddlewares(); ``` vào route để phân quyền

## 4. Cấu hình cơ bản:

#### Sửa đổi để sử dụng JAVA 8
- Chuột phải dự án -> Properties -> Sources -> Source/Binary Format -> Đổi sang 1.8 

## NẾU CẢM THẤY HAY, ỦNG HỘ MÌNH BẰNG CÁCH DƯỚI ĐÂY! 

<img  src="https://github.com/unclecatvn/BaseJava/assets/22569541/434da0cb-50f5-491d-8321-7f31ec4db3ac"  alt="Database"  width="35%"></img>

