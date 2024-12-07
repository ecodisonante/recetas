package com.abueladigital.frontend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.abueladigital.frontend.model.Recipe;
import com.abueladigital.frontend.service.RecipeService;

class RecipeControllerTest {

    @Mock
    private RecipeService service;

    @Mock
    private Model model;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecetaById() {
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(1L);
        mockRecipe.setName("Receta de prueba");

        // Simulamos la llamada al servicio
        when(service.getById(1L)).thenReturn(mockRecipe);

        // Llamada al método
        String viewName = recipeController.getRecetaById(1L, model);

        // Verificación del comportamiento
        verify(service).getById(1L);
        verify(model).addAttribute("recipe", mockRecipe);
        verify(model).addAttribute("content", "Recipe");

        // Verificamos la respuesta
        assertEquals("layout", viewName);
    }

    @Test
    void testCreateRecipe() {
        // Datos de entrada
        String name = "Receta de prueba";
        String description = "Receta deliciosa de prueba";
        Integer servings = 4;
        String country = "Chile";
        Integer difficulty = 2;
        String imageUrl = "http://example.com/image.jpg";
        String ingredients = "Harina:2 tazas\nAzúcar:1 taza";
        String instructions = "Mezclar los ingredientes\nHornear durante 30 minutos";

        // Simulamos el servicio
        Recipe createdRecipe = new Recipe();
        createdRecipe.setId(1L);
        when(service.postRecipe(any(Recipe.class))).thenReturn(createdRecipe);

        // Llamada al método
        String viewName = recipeController.createRecipe(
                name, description, servings, country, difficulty,
                imageUrl, ingredients, instructions, model);

        // Verificación del comportamiento
        verify(service).postRecipe(any(Recipe.class));
        assertEquals("redirect:/home", viewName);
    }

    @Test
    void testCreateRecipeInvalidIngredientFormat() {

        String name = "Receta de prueba";
        String description = "Receta deliciosa de prueba";
        Integer servings = 4;
        String country = "Chile";
        Integer difficulty = 2;
        String imageUrl = "http://example.com/image.jpg";
        String ingredients = "Harina2 tazas\nAzúcar:1 taza";
        String instructions = "Mezclar los ingredientes\nHornear durante 30 minutos";

        // Llamada al método
        String viewName = recipeController.createRecipe(
                name, description, servings, country, difficulty,
                imageUrl, ingredients, instructions, model);

        // Verificación del error
        verify(model).addAttribute(eq("error"), anyString());
        assertEquals("error", viewName);
    }

    @Test
    void testShowRecipeForm() {
        // Llamada al método
        String viewName = recipeController.showRecipeForm(model);

        // Verificación de la receta
        verify(model).addAttribute(eq("Recipe"), any(Recipe.class));
        verify(model).addAttribute("title", "Publicar una nueva receta");
        verify(model).addAttribute("content", "RecipeForm");

        // Verificamos la respuesta
        assertEquals("layout", viewName);
    }
}