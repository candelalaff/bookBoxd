package com.proyecto.bookBoxd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaPersonalizadaDto {
    private Long id;
    private String nombre;
    private String description;
    private boolean esPublica;
    private Long usuarioId;
    private Set<Long> librosIds; // Entregamos solo IDs para mantener el JSON plano
}