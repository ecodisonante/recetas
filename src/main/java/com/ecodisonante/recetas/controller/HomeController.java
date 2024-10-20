package com.ecodisonante.recetas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    public static final String TITLE = "La Abuela Digital";

    @GetMapping("/home")
    public String home(
            @RequestParam(name = "name", required = false, defaultValue = TITLE) String name,
            Model model) {
        model.addAttribute("name", name);
        // Establecer el título y fragmento de contenido para la página de login
        model.addAttribute("content", "Home"); // Se refiere al template login.html
        return "layout"; // usar plantilla "layout.html" como base
    }

    @GetMapping("/")
    public String root(
            @RequestParam(name = "name", required = false, defaultValue = TITLE) String name,
            Model model) {
        model.addAttribute("name", name);
        // Establecer el título y fragmento de contenido para la página de login
        model.addAttribute("content", "Home"); // Se refiere al template login.html
        return "layout"; // usar plantilla "layout.html" como base
    }

}
