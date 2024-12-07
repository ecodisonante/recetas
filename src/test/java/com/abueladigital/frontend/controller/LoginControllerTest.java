package com.abueladigital.frontend.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private Model model;

    @InjectMocks
    private LoginController loginController;

    @Test
    void shouldAddAttributesToModelAndReturnLayoutView() {
        // Actuar
        String viewName = loginController.login(model);

        // Afirmar
        assertEquals("layout", viewName, "Se esperaba que el nombre de la vista fuera 'layout'");
        verify(model).addAttribute("title", "Iniciar Sesión");
        verify(model).addAttribute("content", "Login");
        verifyNoMoreInteractions(model);
    }

    @Test
    void shouldLogoutAndRedirectToHome() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);
    
        // Actuar
        String viewName = loginController.logout(request, response, authentication);
    
        // Afirmar
        assertEquals("redirect:/home", viewName, "Se esperaba redirigir a '/home' después de cerrar sesión");
    }
}
