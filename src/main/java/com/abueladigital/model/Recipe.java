package com.abueladigital.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recipe {

    private Long id;
    private String name;
    private String description;
    private Integer servings;
    private String country;
    private Integer dificulty;
    private String imageUrl;
    private Double rate;
    private List<Ingredient> ingredients;
    private List<Instruction> instructions;
    private Long authorId;
    private LocalDateTime created;

    public Recipe() {
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }

}
