package com.abueladigital.frontend.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

// heredaamos MockitoExtension para habilitar el uso de Mockito en las pruebas
@ExtendWith(MockitoExtension.class)  
class LoginControllerTest {

    // mock para el modelo entrada login
    @Mock
    private Model model;

    // se inyecta LoginController para realizar prueba mock logincontroller
    @InjectMocks
    private LoginController loginController;

    // probando la función login de LoginController
    @Test
    void testLogin() {
        // realizamos llamada metodo login de LoginController
        String viewName = loginController.login(model);

        // asserts que retorne la vista layout
        assertEquals("layout", viewName);

        // asignamos atributos correctos fueron agregados al modelo
        verify(model).addAttribute("title", "Iniciar Sesión");
        verify(model).addAttribute("content", "Login");
    }
}
