package com.abueladigital.frontend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

// decoraador instancia el contexto completo de spring
@SpringBootTest

// decorador configura automáticamente Mock mvc para realizar pruebas de integracion  
@AutoConfigureMockMvc  
class LoginControllerIntegrationTest {

    // se inyecta el objeto Mock mvc para hacer llamadaas http
    @Autowired
    private MockMvc mockMvc;  

    @Test
    void testLogin() throws Exception {
        // realizamos solicitud get simulada a la ruta /login
        mockMvc.perform(get("/login")) 
            // verificando que el estado de la respuesta sea 200 OK
            .andExpect(status().isOk())
            // verificando que el nombre de la vista devuelta sea layout  
            .andExpect(view().name("layout"))
            // verificando que el atributo title del modelo sea Iniciar Sesión 
            .andExpect(model().attribute("title", "Iniciar Sesión"))
            // verificando que el atributo content del modelo sea Login  
            .andExpect(model().attribute("content", "Login"));  
    }
}
