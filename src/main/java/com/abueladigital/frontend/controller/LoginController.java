package com.abueladigital.frontend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        // Establecer el título y fragmento de contenido para la página de login
        model.addAttribute("title", "Iniciar Sesión");
        model.addAttribute("content", "Login"); // Se refiere al template login.html
        return "layout"; // usar plantilla "layout.html" como base
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/home"; // Redirige a /home después del logout
    }
}
