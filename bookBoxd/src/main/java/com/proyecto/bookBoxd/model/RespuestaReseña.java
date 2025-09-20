package com.proyecto.bookBoxd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaReseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo para el contenido de la respuesta.
    // @NotBlank asegura que el campo no esté vacío.
    // @Size limita la longitud del texto.
    @NotBlank(message = "El contenido de la respuesta no puede estar vacío.")
    @Size(max = 500, message = "El contenido de la respuesta no puede exceder los 500 caracteres.")
    private String contenido;

    // @CreationTimestamp genera automáticamente la fecha y hora de creación.
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    // Relación con el usuario que hizo la respuesta.
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relación con la reseña a la que se responde.
    @ManyToOne
    @JoinColumn(name = "reseña_id")
    private Reseña reseña;
}