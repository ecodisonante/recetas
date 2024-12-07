package com.abueladigital.frontend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.abueladigital.frontend.model.Recipe;
import com.abueladigital.frontend.model.TokenStorage;

class RecipeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TokenStorage tokenStorage;

    @Mock
    private Environment env;

    private RecipeService recipeService;

    private final String baseUrl = "http://localhost:8081/api/recipes";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simulando la URL base del entorno a localhost
        when(env.getProperty("backend.base.url")).thenReturn("http://localhost:8081");

        // Instanciando el servicio
        recipeService = new RecipeService(restTemplate, tokenStorage, env);
    }


    @SuppressWarnings("unchecked")
    @Test
    void testGetAll() {
        // Respuesta simulada
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setName("Receta 1");
    
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Receta 2");
    
        List<Recipe> mockRecipes = Arrays.asList(recipe1, recipe2);
        ResponseEntity<List<Recipe>> mockResponse = new ResponseEntity<>(mockRecipes, HttpStatus.OK);
    
        when(restTemplate.exchange(
                eq("http://localhost:8081/api/recipes"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockResponse);
    
        // Llamada al método
        List<Recipe> recipes = recipeService.getAll();
    
        // Verificación
        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        assertEquals("Receta 1", recipes.get(0).getName());
    }
    

    @Test
    void testGetById() {
        // Respuesta simulada
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(1L);
        mockRecipe.setName("Receta Simulada");

        ResponseEntity<Recipe> mockResponse = new ResponseEntity<>(mockRecipe, HttpStatus.OK);

        when(tokenStorage.getToken()).thenReturn("mock-token");
        when(restTemplate.exchange(
                eq(baseUrl + "/1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Recipe.class))
        ).thenReturn(mockResponse);

        // Llamada al método
        Recipe recipe = recipeService.getById(1L);

        // Verificación
        assertNotNull(recipe);
        assertEquals(1L, recipe.getId());
        assertEquals("Receta Simulada", recipe.getName());
    }

    @Test
    void testPostRecipe() {
        // Entrada simulada
        Recipe inputRecipe = new Recipe();
        inputRecipe.setName("Nueva Receta");

        Recipe mockResponseRecipe = new Recipe();
        mockResponseRecipe.setId(1L);
        mockResponseRecipe.setName("Nueva Receta");

        ResponseEntity<Recipe> mockResponse = new ResponseEntity<>(mockResponseRecipe, HttpStatus.CREATED);

        when(tokenStorage.getToken()).thenReturn("mock-token");
        when(restTemplate.exchange(
                eq(baseUrl),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Recipe.class))
        ).thenReturn(mockResponse);

        // Llamada al método
        Recipe createdRecipe = recipeService.postRecipe(inputRecipe);

        // Verificación
        assertNotNull(createdRecipe);
        assertEquals(1L, createdRecipe.getId());
        assertEquals("Nueva Receta", createdRecipe.getName());
    }

    @Test
    void testPostRecipeFailure() {
        // Entrada simulada
        Recipe inputRecipe = new Recipe();
        inputRecipe.setName("Nueva Receta");

        ResponseEntity<Recipe> mockResponse = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        when(tokenStorage.getToken()).thenReturn("mock-token");
        when(restTemplate.exchange(
                eq(baseUrl),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Recipe.class))
        ).thenReturn(mockResponse);

        // Llamada al método y esperamos una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            recipeService.postRecipe(inputRecipe);
        });

        // Verificación
        assertEquals("Failed to create recipe", exception.getMessage());
    }
}
