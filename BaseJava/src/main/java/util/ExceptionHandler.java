/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Hoai Nam
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExceptionHandler {

    public static void handleException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        try {
            if (e instanceof ServletException) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } else if (e instanceof IOException) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
