package com.proyecto.bookBoxd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity // Esta clase será una entidad persistente (tabla en la BD)
@Data // Lombok: genera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Constructor vacío (requerido por JPA)
@AllArgsConstructor // Constructor con todos los campos
@Builder // Permite usar el patrón builder para crear objetos
public class Autor {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    // Validamos que el nombre no esté en blanco y tenga una longitud razonable
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    // Biografía opcional, pero limitada en longitud
    @Size(max = 1000, message = "La biografía no puede tener más de 1000 caracteres")
    private String biografia;

    // Relación muchos a muchos con libros:
    // Un autor puede escribir muchos libros, y un libro puede tener varios autores
    @ManyToMany(mappedBy = "autores")
    @Builder.Default // Evita conflictos con @Builder al inicializar colecciones
    private Set<Libro> libros = new HashSet<>();
}
