package com.abueladigital.frontend.service;

import org.springframework.core.ParameterizedTypeReference;
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

    //@Autowired
    public RecipeService(RestTemplate restTemplate, TokenStorage tokenStorage) {
        this.restTemplate = restTemplate;
        this.tokenStorage = tokenStorage;
    }

    public List<Recipe> getAll() {
        String url = "http://backend:8081/api/recipes";
        ResponseEntity<List<Recipe>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recipe>>() {
                });
        return response.getBody();
    }

    public Recipe getById(long id) {
        String url = String.format("http://backend:8081/api/recipes/%d", id);

        // agregar token al header
        HttpHeaders headers = new HttpHeaders();
        String token = tokenStorage.getToken();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
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
        String url = "http://backend:8081/api/recipes";

        HttpHeaders headers = new HttpHeaders();
        String token = tokenStorage.getToken();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, headers);

        ResponseEntity<Recipe> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Recipe.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to create recipe");
        }
    }

}
