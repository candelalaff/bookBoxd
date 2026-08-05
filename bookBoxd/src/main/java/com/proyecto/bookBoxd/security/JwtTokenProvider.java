package com.proyecto.bookBoxd.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

  //Componente encargado de la generación, firmado, parseo y validación de los tokens JWT (JSON Web Tokens).
@Component
public class JwtTokenProvider {

    // Clave secreta de firmado generada automáticamente con algoritmo HMAC-SHA256
    private final Key JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Tiempo de expiración del token: 24 horas en milisegundos
    private final long JWT_EXPIRATION_MS = 86400000;

    //Genera un token JWT a partir de la autenticación exitosa del usuario.
    public String generarToken(Authentication authentication) {
        String username = authentication.getName();
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(JWT_SECRET)
                .compact();
    }

    //Extrae el nombre de usuario (subject) contenido dentro del token.
    
    public String obtenerUsernameDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //Valida la firma y la fecha de expiración del token JWT.
     
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(JWT_SECRET).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}