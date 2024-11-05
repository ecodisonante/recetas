package com.abueladigital.frontend.model;

import lombok.Data;

@Data
public class Instruction {
    private Long id;
    private Integer step;
    private String action;
}