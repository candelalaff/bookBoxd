package com.proyecto.bookBoxd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO para recibir las credenciales en el inicio de sesion
@Data
public class LoginDto {

    // Alias del usuario para autenticarse
    @NotBlank(message = "El alias no puede estar vacio")
    private String alias;

    // Contrasena ingresada por el usuario
    @NotBlank(message = "La contrasena no puede estar vacia")
    private String password;
}