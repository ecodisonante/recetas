package com.abueladigital.frontend.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.abueladigital.frontend.model.Recipe;
import com.abueladigital.frontend.service.RecipeService;

class HomeControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks
    }

    @Test
    void testHome() {
        // Preparar datos simulados
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setName("Receta 1");
        recipe1.setCreated(LocalDateTime.now());
        recipe1.setRate(4.5);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Receta 2");
        recipe2.setCreated(LocalDateTime.now().minusDays(1));
        recipe2.setRate(5.0);

        Recipe recipe3 = new Recipe();
        recipe3.setId(3L);
        recipe3.setName("Receta 3");
        recipe3.setCreated(LocalDateTime.now().minusDays(2));
        recipe3.setRate(4.0);

        List<Recipe> allRecipes = Arrays.asList(recipe1, recipe2, recipe3);

        // Configurar comportamiento del servicio simulado
        when(recipeService.getAll()).thenReturn(allRecipes);

        // Llamar al método del controlador
        String viewName = homeController.home("La Abuela Digital", model);

        // Verificar que se llama al servicio
        verify(recipeService).getAll();

        // Verificar que los datos se añaden al modelo
        verify(model).addAttribute("name", "La Abuela Digital");
        verify(model).addAttribute(eq("latest"), anyList());
        verify(model).addAttribute(eq("popular"), anyList());
        verify(model).addAttribute("content", "Home");

        // Verificar que la vista devuelta es "layout"
        assertEquals("layout", viewName);
    }
}
