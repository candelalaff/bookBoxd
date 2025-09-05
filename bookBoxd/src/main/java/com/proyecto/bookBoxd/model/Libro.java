package com.proyecto.bookBoxd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data // Lombok genera getters, setters, toString, equals y hashCode (¡cuidado con relaciones bidireccionales!)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Título obligatorio, con mínimo 1 y máximo 100 caracteres
    @NotBlank(message = "El título no puede estar vacío.")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres.")
    private String titulo;

    // Descripción opcional, pero con límite de caracteres
    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres.")
    private String descripcion;

    // Género opcional, con límite de caracteres
    @Size(max = 50, message = "El género no puede tener más de 50 caracteres.")
    private String genero;

    // Muchos a muchos con autores. Se crea una tabla intermedia automáticamente.
    @ManyToMany
    @Builder.Default // Evita que @Builder ignore esta inicialización
    private Set<Autor> autores = new HashSet<>();

    // Uno a muchos con reseñas. Un libro tiene muchas reseñas. Cascade permite que se guarden automáticamente.
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Reseña> reseñas = new HashSet<>();

    // Muchos a muchos inversa con listas personalizadas
    @ManyToMany(mappedBy = "libros")
    @Builder.Default
    private Set<ListaPersonalizada> listas = new HashSet<>();
}
