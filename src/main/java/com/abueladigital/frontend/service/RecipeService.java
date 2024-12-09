package com.abueladigital.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.abueladigital.frontend.model.Recipe;
import com.abueladigital.frontend.model.TokenStorage;

import java.util.List;

@Service
public class RecipeService {

    private final RestTemplate restTemplate;
    private final TokenStorage tokenStorage;
    private final String baseUrl;

    @Autowired
    public RecipeService(RestTemplate restTemplate, TokenStorage tokenStorage, Environment env) {
        this.restTemplate = restTemplate;
        this.tokenStorage = tokenStorage;
        baseUrl = env.getProperty("backend.base.url") + "/api/recipes";
    }

    public List<Recipe> getAll() {
        ResponseEntity<List<Recipe>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recipe>>() {
                });
        return response.getBody();
    }

    public Recipe getById(long id) {
        String url = String.format("%s/%d", baseUrl, id);

        // agregar token al header
        HttpHeaders headers = new HttpHeaders();
        String token = tokenStorage.getToken();
        if (token != null) {
            headers.setBearerAuth(token);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Recipe> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Recipe.class);

        return response.getBody();
    }

    public Recipe postRecipe(Recipe recipe) {
        HttpHeaders headers = new HttpHeaders();
        String token = tokenStorage.getToken();
        if (token != null) {
            headers.setBearerAuth(token);
        }

        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, headers);

        ResponseEntity<Recipe> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                Recipe.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new RecipeCreationException("Failed to create recipe");
        }
    }

    public static class RecipeCreationException extends RuntimeException {
        public RecipeCreationException(String message) {
            super(message);
        }

        public RecipeCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
