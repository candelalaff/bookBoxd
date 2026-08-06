package com.proyecto.bookBoxd.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Clase principal de configuración para la seguridad de Spring Security y JWT
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Inyección de dependencias del filtro personalizado mediante constructor
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Define la cadena de filtros de seguridad HTTP (SecurityFilterChain)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Configura la política CORS utilizando el bean definido más abajo
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. Deshabilita CSRF (Cross-Site Request Forgery) ya que trabajamos con API REST sin estado
            .csrf(csrf -> csrf.disable())
            
            // 3. Define la gestión de sesiones como STATELESS (sin estado, dependemos 100% de los tokens JWT)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 4. Reglas de autorización para los endpoints
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Habilita login y registro sin autenticación previa
                .anyRequest().authenticated()               // Exige autenticación token JWT para el resto de la API
            )
            
            // 5. Agrega el filtro JWT personalizado antes del filtro de autenticación por defecto de Spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuración global de CORS para habilitar peticiones desde el cliente Angular
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permite el origen exacto donde se ejecuta la aplicación Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        
        // Define los métodos HTTP autorizados
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Permite cualquier cabecera (incluidas Authorization y Content-Type)
        configuration.setAllowedHeaders(List.of("*"));
        
        // Permite el envío de credenciales/cookies si fuera necesario
        configuration.setAllowCredentials(true);

        // Aplica esta configuración a todas las rutas de la aplicación
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Bean para el encriptado y verificación segura de contraseñas usando el algoritmo BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean encargado de gestionar el proceso de autenticación de usuarios dentro de Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}