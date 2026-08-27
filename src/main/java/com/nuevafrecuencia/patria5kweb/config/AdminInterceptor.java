package com.nuevafrecuencia.patria5kweb.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean authenticated = session != null && Boolean.TRUE.equals(session.getAttribute("ADMIN_AUTH"));
        if (authenticated) return true;
        response.sendRedirect("/admin/login");
        return false;
    }
}
