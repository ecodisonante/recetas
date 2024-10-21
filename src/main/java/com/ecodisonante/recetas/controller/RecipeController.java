package com.ecodisonante.recetas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecodisonante.recetas.model.DataLoader;

@Controller
public class RecipeController {

    public static final String TITLE = "La Abuela Digital";

    // TODO: Reemplazar por id dinammico
    @GetMapping("/recipe/{id}")
    public String getRecetaById(@PathVariable("id") Long id, Model model) {
        // Buscar receta
        var recipe = DataLoader.loadRecipes().stream().filter(x -> x.getId().equals(id)).findFirst();
        // Añadir la receta al modelo
        model.addAttribute("recipe", recipe.get());
        model.addAttribute("content", "Recipe"); // Se refiere al template recipe1.html

        return "layout"; // usar plantilla "layout.html" como base
    }

}
