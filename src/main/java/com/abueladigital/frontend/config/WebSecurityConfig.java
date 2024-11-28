package com.abueladigital.frontend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    CustomAuthenticationProvider authProvider;

    @Autowired
    public WebSecurityConfig(CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    private static String home = "home";

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(this.authProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .headers(headers -> headers.contentSecurityPolicy(
                        cps -> cps.policyDirectives("default-src 'self'; "
                                + "img-src 'self' https://i.blogs.es;"
                                + "script-src 'self' https://cdn.jsdelivr.net; "
                                + "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; "
                                + "font-src 'self' http://themes.googleusercontent.com; "
                                + "media-src 'self'; "
                                + "object-src 'none'; "
                                + "base-uri 'self'; "
                                + "form-action 'self' http://localhost:* http://backend:* http://48.211.162.254:*; "
                                + "frame-ancestors 'self'; "
                                + "connect-src 'self' http://localhost:* http://backend:* http://48.211.162.254:*;")))

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/" + home, "/login").permitAll()
                        .requestMatchers("/css/**.css").permitAll()
                        .requestMatchers("/img/**.jpg").permitAll()
                        .requestMatchers("/img/recipe/**.jpg").permitAll()
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/" + home, true)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/" + home)
                        .permitAll())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)); // Usa sesiones cuando sea necesario

        return http.build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration configuration = new CorsConfiguration();
    // configuration.setAllowCredentials(true); // Permite el envío de cookies
    // configuration.addAllowedOrigin("http://localhost:8082"); // Origen del
    // frontend
    // configuration.addAllowedOrigin("http://frontend:8082"); // Origen del
    // frontend
    // configuration.addAllowedOrigin("http://48.211.162.254:8082"); // Origen del
    // frontend
    // configuration.addAllowedMethod("*");
    // configuration.addAllowedHeader("*");

    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", configuration);
    // return source;
    // }

}