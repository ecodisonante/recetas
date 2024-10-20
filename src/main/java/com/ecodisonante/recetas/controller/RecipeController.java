package com.ecodisonante.recetas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecipeController {

    public static final String TITLE = "La Abuela Digital";

    // TODO: Reemplazar por id dinammico
    @GetMapping("/recipe/1")
    public String home(
            @RequestParam(name = "name", required = false, defaultValue = TITLE) String name,
            Model model) {
        model.addAttribute("content", "Recipe1"); // Se refiere al template login.html
        return "layout"; // usar plantilla "layout.html" como base
    }

}
