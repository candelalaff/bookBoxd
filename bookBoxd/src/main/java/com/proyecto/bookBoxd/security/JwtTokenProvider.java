package com.proyecto.bookBoxd.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

// Componente encargado de la generación, firmado, parseo y validación de los tokens JWT (JSON Web Tokens).
@Component
public class JwtTokenProvider {

    // Clave secreta de firmado, leída desde application.properties (app.jwt.secret).
    // IMPORTANTE: antes se generaba con Keys.secretKeyFor(...), lo que creaba una clave
    // ALEATORIA nueva en cada reinicio del servidor. Eso invalidaba todos los tokens
    // emitidos antes de reiniciar la app (por eso fallaban logins "viejos").
    // Ahora la clave es fija y se define una sola vez en application.properties.
    @Value("${app.jwt.secret}")
    private String jwtSecretString;

    // Tiempo de expiración del token: 24 horas en milisegundos
    private final long JWT_EXPIRATION_MS = 86400000;

    // Construye la Key HMAC a partir del string de application.properties
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretString.getBytes(StandardCharsets.UTF_8));
    }

    // Genera un token JWT a partir de la autenticación exitosa del usuario.
    public String generarToken(Authentication authentication) {
        String username = authentication.getName();
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrae el nombre de usuario (subject) contenido dentro del token.
    public String obtenerUsernameDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Valida la firma y la fecha de expiración del token JWT.
    // Nunca deja escapar la excepción: si el token es inválido/expirado, devuelve false.
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}