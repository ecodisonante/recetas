package com.abueladigital.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    @Autowired
    public RecipeService(RestTemplate restTemplate, TokenStorage tokenStorage) {
        this.restTemplate = restTemplate;
        this.tokenStorage = tokenStorage;
    }

    public List<Recipe> getAll() {
        String url = "http://localhost:8081/api/recipes";
        ResponseEntity<List<Recipe>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recipe>>() {
                });
        return response.getBody();
    }

    public Recipe getById(long id) {
        String url = String.format("http://localhost:8081/api/recipes/%d", id);

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

}
