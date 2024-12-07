package com.abueladigital.frontend.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import com.abueladigital.frontend.model.TokenStorage;


class CustomAuthenticationProviderTest {

    @Mock
    private TokenStorage tokenStorage;

    @Mock
    private Environment env;

    @Mock
    private RestTemplate restTemplate;

    private CustomAuthenticationProvider authenticationProvider;
    private final String baseUrl = "http://localhost:8081/api/auth/login";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simulamos la URL base del entorno para incluir el número de puerto
        when(env.getProperty("backend.base.url")).thenReturn("http://localhost:8081");

        // Instanciamos el proveedor de autenticación con RestTemplate simulado
        authenticationProvider = new CustomAuthenticationProvider(tokenStorage, env);
    }

    // Test para verificar si se lanza una excepción cuando hay un error en el servicio (por ejemplo, servicio no disponible)
    @Test
    void testAuthenticateServiceError() {
        // Datos de entrada simulados
        String username = "juan@test.com";
        String password = "Secret123";

        // Simulamos RestTemplate para lanzar una RestClientException (simulando que el servicio no está disponible)
        when(restTemplate.postForEntity(eq(baseUrl), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RestClientException("Service unavailable"));

        // Llamamos al método authenticate y esperamos que se lance una excepción
        assertThrows(AuthenticationServiceException.class, () -> {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        });
    }

    // Test para verificar si se lanza una excepción cuando hay un error al procesar el JSON
    @Test
    void testAuthenticateJsonProcessingError() {
        // Datos de entrada y respuesta simulada
        String username = "testuser";
        String password = "testpass";

        // Simulamos que RestTemplate devuelve una respuesta que causa un error al analizar el JSON
        String malformedJsonResponse = "invalid-json";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(malformedJsonResponse, HttpStatus.OK);

        // Simulamos el comportamiento de RestTemplate
        when(restTemplate.postForEntity(eq(baseUrl), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        // Llamamos al método authenticate y esperamos que se lance una excepción
        assertThrows(AuthenticationServiceException.class, () -> {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        });
    }

    // Test para verificar que el proveedor de autenticación soporta el tipo UsernamePasswordAuthenticationToken
    @Test
    void testSupports() {
        // Verificamos que el proveedor de autenticación soporta UsernamePasswordAuthenticationToken
        assertTrue(authenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
    }
}
