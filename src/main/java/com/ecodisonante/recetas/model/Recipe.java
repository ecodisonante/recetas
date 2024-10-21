package com.ecodisonante.recetas.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    private Long id;
    private String name;
    private String description;
    private List<String> ingredients;
    private String instructions;
    private Integer servings;
    private String country;
    private Integer dificulty;
    private String imageUrl;
    private Long authorId;
    private LocalDateTime createdAt;
    private Double rate;

}
