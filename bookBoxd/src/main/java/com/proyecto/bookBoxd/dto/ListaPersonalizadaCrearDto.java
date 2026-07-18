package com.proyecto.bookBoxd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaPersonalizadaCrearDto {

    @NotBlank(message = "El nombre de la lista no puede estar vacío.")
    private String nombre;

    private String description;

    private boolean esPublica = true;

    @NotNull(message = "El ID de usuario es obligatorio.")
    private Long usuarioId;

    // Desde Angular mandamos simplemente los IDs de los libros seleccionados para la lista
    private Set<Long> librosIds;
}