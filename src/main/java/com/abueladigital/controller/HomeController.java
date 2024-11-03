package com.abueladigital.controller;

import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abueladigital.model.Recipe;
import com.abueladigital.service.RecipeService;


@Controller
public class HomeController {

    @Autowired
    private RecipeService service;

    public static final String TITLE = "La Abuela Digital";

    @GetMapping(value = { "/", "/home" })
    public String home(
            @RequestParam(name = "name", required = false, defaultValue = TITLE) String name,
            Model model) {

        var allRecipes = service.getAll();

        var latest = allRecipes.stream()
                .sorted(Comparator.comparing(Recipe::getCreated).reversed())
                .limit(3)
                .collect(Collectors.toList());

        var popular = allRecipes.stream()
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
