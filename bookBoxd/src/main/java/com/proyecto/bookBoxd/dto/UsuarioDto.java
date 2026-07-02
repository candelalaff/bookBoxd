package com.proyecto.bookBoxd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


// @NoArgsConstructor y @AllArgsConstructor: Crean constructores vacíos y con todos los argumentos, respectivamente.
@Data
@NoArgsConstructor
@AllArgsConstructor
//este dto contiene los datos que quiero exponer: id, usuario y email.
public class UsuarioDto {
    
    // El ID es necesario para identificar al usuario al recuperarlo.
    private Long id;
    
    // anotaciones de validación que aseguran que el nombre de usuario 
    // cumpla con los requisitos antes de ser procesado por el controlador.
    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres.")
    private String alias;
    
    // validación @Email asegura que el formato del correo sea válido.
    @NotBlank(message = "El correo electrónico no puede estar vacío.")
    @Email(message = "El formato del correo electrónico es inválido.")
    private String email;
}