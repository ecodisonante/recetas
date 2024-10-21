package com.ecodisonante.recetas.controller;

import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecodisonante.recetas.model.DataLoader;
import com.ecodisonante.recetas.model.Recipe;

@Controller
public class HomeController {

    public static final String TITLE = "La Abuela Digital";

    @GetMapping(value = {"/", "/home"})
    public String home(
            @RequestParam(name = "name", required = false, defaultValue = TITLE) String name,
            Model model) {

        var latest = DataLoader.loadRecipes().stream()
                .sorted(Comparator.comparing(Recipe::getCreatedAt).reversed())
                .limit(3)
                .collect(Collectors.toList());

        var popular = DataLoader.loadRecipes().stream()
                .sorted(Comparator.comparing(Recipe::getRate).reversed())
                .limit(3)
                .collect(Collectors.toList());

        model.addAttribute("name", name);
        model.addAttribute("latest", latest);
        model.addAttribute("popular", popular);

        // Establecer el título y fragmento de contenido para la página de login
        model.addAttribute("content", "Home"); // Se refiere al template login.html
        return "layout"; // usar plantilla "layout.html" como base
    }

}
