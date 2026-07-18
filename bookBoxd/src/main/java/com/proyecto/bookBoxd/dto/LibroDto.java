package com.proyecto.bookBoxd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String genero;
    private Set<Long> autoresIds; // Mandamos solo los IDs para no sobrecargar el JSON
}