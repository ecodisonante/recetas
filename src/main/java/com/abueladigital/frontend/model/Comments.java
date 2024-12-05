package com.abueladigital.frontend.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comments {
    private Long id;
    private String content;
    private LocalDateTime created;
}
