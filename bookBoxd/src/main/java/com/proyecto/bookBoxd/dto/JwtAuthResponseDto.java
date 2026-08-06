package com.proyecto.bookBoxd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para enviar la respuesta del token JWT al cliente
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponseDto {

    // El token JWT generado por la aplicacion
    private String accessToken;

    // Tipo de token para las cabeceras de autorizacion
    private String tokenType = "Bearer";

    // Constructor conveniente para inicializar solo el token
    public JwtAuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}