package com.proyecto.bookBoxd.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReseñaCrearDto {

    @NotBlank(message = "El cuerpo de la reseña no puede estar vacío.")
    @Size(min = 1, max = 500, message = "La reseña debe tener entre 1 y 500 caracteres.")
    private String cuerpo;

    @NotNull(message = "La calificación es obligatoria.")
    @DecimalMin(value = "0.5", message = "La calificación debe ser al menos 0.5.")
    @DecimalMax(value = "5.0", message = "La calificación no puede ser mayor a 5.")
    private Double calificacion;

    @NotNull(message = "El ID de usuario es obligatorio.")
    private Long usuarioId;

    @NotNull(message = "El ID de libro es obligatorio.")
    private Long libroId;
}