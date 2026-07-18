package com.proyecto.bookBoxd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroCrearDto {

    @NotBlank(message = "El título no puede estar vacío.")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres.")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres.")
    private String descripcion;
    
    @Size(max = 50, message = "El género no puede tener más de 50 caracteres.")
    private String genero;

    @NotEmpty(message = "El libro debe tener al menos un autor asociado.")
    private Set<Long> autoresIds;
}