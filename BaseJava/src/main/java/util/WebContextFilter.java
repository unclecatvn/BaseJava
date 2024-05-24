package util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "WebContextFilter", urlPatterns = {"/*"})
public class WebContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        WebContext.setCurrentRequest((HttpServletRequest) request);
        WebContext.setCurrentResponse((HttpServletResponse) response);
        try {
            chain.doFilter(request, response);
        } finally {
            WebContext.clear();
        }
    }
}
