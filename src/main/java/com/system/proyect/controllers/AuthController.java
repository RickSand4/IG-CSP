package com.system.proyect.controllers;

import com.system.proyect.models.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    // Simula usuarios
    private static final Map<String, Usuario> users = new HashMap<>();
    static {
        users.put("estudiante", new Usuario("estudiante", "ESTUDIANTE"));
        users.put("admin", new Usuario("admin", "ADMINISTRADOR"));
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
        public String processLogin(@RequestParam String username, @RequestParam String password,
                               HttpSession session, RedirectAttributes redirectAttrs) {
        // Validación simple (contraseña fija: "1234")
        if (users.containsKey(username) && "1234".equals(password)) {
            Usuario user = users.get(username);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userAvatar", user.getAvatar());
            return "redirect:/dashboard";
        }
        redirectAttrs.addFlashAttribute("error", "Credenciales inválidas");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}