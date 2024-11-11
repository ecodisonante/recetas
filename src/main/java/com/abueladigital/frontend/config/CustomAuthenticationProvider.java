package com.abueladigital.frontend.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.abueladigital.frontend.model.AuthRequest;
import com.abueladigital.frontend.model.TokenStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private TokenStorage tokenStorage;

    public CustomAuthenticationProvider(TokenStorage tokenStore) {
        super();
        this.tokenStorage = tokenStore;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();

        try {
            // Configurar encabezados y cuerpo de la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            AuthRequest authRequest = new AuthRequest(name, password);
            HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(authRequest, headers);

            // Realizar la solicitud de autenticación al backend
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    "http://localhost:8081/api/auth/login",
                    requestEntity,
                    String.class);

            // Verificar el estado de la respuesta
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new BadCredentialsException("Invalid username or password");
            }

            // Extraer el token del cuerpo de la respuesta
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseEntity.getBody());
            String token = jsonNode.get("token").asText();
            tokenStorage.setToken(token);

            // Asignar roles de usuario
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return new UsernamePasswordAuthenticationToken(name, password, authorities);

        } catch (RestClientException e) {
            // Error al realizar la solicitud HTTP
            throw new AuthenticationServiceException("Error al conectar con el servicio de autenticación", e);

        } catch (JsonProcessingException e) {
            // Error al procesar el JSON de la respuesta
            throw new AuthenticationServiceException("Error al procesar la respuesta del servicio de autenticación", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}