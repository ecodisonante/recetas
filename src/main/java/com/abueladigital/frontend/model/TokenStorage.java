package com.abueladigital.frontend.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class TokenStorage {

    private String token;

}
