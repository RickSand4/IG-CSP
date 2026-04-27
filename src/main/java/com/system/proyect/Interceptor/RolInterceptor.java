package com.system.proyect.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RolInterceptor implements HandlerInterceptor {

    private final String[] allowedRoles;

    public RolInterceptor(String... roles) {
        this.allowedRoles = roles;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userRole = (String) request.getSession().getAttribute("userRole");
        if (userRole == null) {
            response.sendRedirect("/login");
            return false;
        }
        for (String role : allowedRoles) {
            if (role.equals(userRole)) {
                return true;
            }
        }
        response.sendError(403, "Acceso denegado");
        return false;
    }
}