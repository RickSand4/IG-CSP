package com.system.proyect.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 1. Visitantes: Página pública
    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("userRole") != null) {
            return "redirect:/login";
        }
        return "index"; // inicio de sesion
    }

    // 2. Usuarios Logueados: El Lobby/Centro de control
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    // 3. LA ARENA: El entorno de competencia
    @GetMapping("/arena")
    public String arena() {
        return "arena"; // Apunta a tu interfaz del editor de código
    }
}