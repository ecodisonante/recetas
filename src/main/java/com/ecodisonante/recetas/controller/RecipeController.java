package com.ecodisonante.recetas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecodisonante.recetas.service.RecipeService;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService service;

    public static final String TITLE = "La Abuela Digital";

    @GetMapping("/recipe/{id}")
    public String getRecetaById(@PathVariable("id") Long id, Model model) {
        
        // Buscar receta
        var recipe = service.getById(id);

        // Añadir la receta al modelo
        model.addAttribute("recipe", recipe);
        model.addAttribute("content", "Recipe"); 

        return "layout"; // usar plantilla "layout.html" como base
    }

}
