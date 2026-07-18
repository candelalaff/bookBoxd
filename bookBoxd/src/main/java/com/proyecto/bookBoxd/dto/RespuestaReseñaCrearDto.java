package com.proyecto.bookBoxd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaReseñaCrearDto {

    @NotBlank(message = "El contenido de la respuesta no puede estar vacío.")
    @Size(max = 500, message = "El contenido de la respuesta no puede exceder los 500 caracteres.")
    private String contenido;

    @NotNull(message = "El ID de usuario es obligatorio.")
    private Long usuarioId;

    @NotNull(message = "El ID de la reseña es obligatorio.")
    private Long reseñaId;
}