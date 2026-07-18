package com.proyecto.bookBoxd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaReseñaDto {
    private Long id;
    private String contenido;
    private LocalDateTime fechaCreacion;
    private Long usuarioId;
    private Long reseñaId;
}