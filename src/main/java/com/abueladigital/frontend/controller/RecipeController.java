package com.abueladigital.frontend.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abueladigital.frontend.model.Ingredient;
import com.abueladigital.frontend.model.Instruction;
import com.abueladigital.frontend.model.Recipe;
import com.abueladigital.frontend.service.RecipeService;


@Controller
public class RecipeController {


    private RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }
    
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

        @PostMapping("/recipe")
    public String createRecipe(   
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestParam("servings") Integer servings,
        @RequestParam("country") String country,
        @RequestParam("difficulty") Integer difficulty,
        @RequestParam("imageUrl") String imageUrl,
        @RequestParam("rate") Double rate,
        @RequestParam("ingredients") String ingredients,
        @RequestParam("instructions") String instructions,
        @RequestParam("authorId") Long authorId,
        @RequestParam("created") String created,
        Model model) {
            
        try {
            // Creando objeto receta
            Recipe recipe = new Recipe();
            recipe.setName(name);
            recipe.setDescription(description);
            recipe.setServings(servings);
            recipe.setCountry(country);
            recipe.setDificulty(difficulty);
            recipe.setImageUrl(imageUrl);
            recipe.setRate(rate);

            // agregando objeto ingredientes
            List<Ingredient> ingredientList = Arrays.stream(ingredients.split(",")).map(ingredient -> {
                String[] parts = ingredient.split(":");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid ingredient format: " + ingredient);
                }
                Ingredient ing = new Ingredient();
                ing.setName(parts[0].trim());
                ing.setAmmount(parts[1].trim());
                return ing;
            })
                    .collect(Collectors.toList());
            recipe.setIngredients(ingredientList);

            // agregando objeto intrucciones
            List<Instruction> instructionList = Arrays.stream(instructions.split(",")).map(instruction -> {
                        String[] parts = instruction.split(":");
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Invalid instruction format: " + instruction);
                        }
                        Instruction inst = new Instruction();
                        inst.setStep(Integer.parseInt(parts[0].trim()));
                        inst.setAction(parts[1].trim());
                        return inst;
                    })
                    .collect(Collectors.toList());
            recipe.setInstructions(instructionList);

            recipe.setAuthorId(authorId);

            // Parse created date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            recipe.setCreated(LocalDateTime.parse(created, formatter));

            System.out.println("Recipe object: " + recipe);

            Recipe createdRecipe = service.postRecipe(recipe);

            return "redirect:/recipe/" + createdRecipe.getId();

        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al agregar la receta: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/recipe")
    public String showRecipeForm(Model model) {
        model.addAttribute("Recipe", new Recipe());
        model.addAttribute("title", "Publicar una nueva receta");
        model.addAttribute("content", "RecipeForm");

        return "layout"; 
    }

}
