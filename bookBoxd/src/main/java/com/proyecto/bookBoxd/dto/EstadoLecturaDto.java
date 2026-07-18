package com.proyecto.bookBoxd.dto;

import com.proyecto.bookBoxd.model.EstadoLectura.Estado;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoLecturaDto {

    @NotNull(message = "El ID de usuario es obligatorio.")
    private Long usuarioId;

    @NotNull(message = "El ID de libro es obligatorio.")
    private Long libroId;

    @NotNull(message = "El estado de lectura es obligatorio.")
    private Estado estado;
}