/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package util;

import util.ExceptionHandler;
import util.WebContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *
 * @author Hoai Nam
 */
@WebFilter(filterName = "WebContextFilter", urlPatterns = {"/*"})
public class WebContextFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        WebContext.setCurrentRequest((HttpServletRequest) request);
        WebContext.setCurrentResponse((HttpServletResponse) response);
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            ExceptionHandler.handleException((HttpServletRequest) request, (HttpServletResponse) response, e);
        } finally {
            WebContext.clear();
        }
    }
}
