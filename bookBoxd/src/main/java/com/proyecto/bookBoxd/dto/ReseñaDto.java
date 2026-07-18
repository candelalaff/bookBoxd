package com.proyecto.bookBoxd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReseñaDto {
    private Long id;
    private String cuerpo;
    private Double calificacion;
    private Long usuarioId;
    private Long libroId;
}