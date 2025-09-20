package com.proyecto.bookBoxd.model;

import jakarta.persistence.*; // Anotaciones para mapeo ORM
import jakarta.validation.constraints.NotBlank; // Validación de campo no vacío
import lombok.*; // Anotaciones Lombok para evitar boilerplate

import java.util.HashSet;
import java.util.Set;

@Entity // esta clase va a ser una entidad persistente (tabla en la BD)
@Data // Lombok: genera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Constructor vacío (requerido por JPA)
@AllArgsConstructor // Constructor con todos los campos
@Builder // Permite usar el patrón builder para crear objetos
public class ListaPersonalizada {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    @NotBlank // Validación: no puede estar vacío
    private String nombre;

    private String descripcion;

    // Por defecto, la lista es pública. Con @Builder.Default, Lombok lo respeta.
    @Builder.Default
    private boolean esPublica = true;

    // Relación con el usuario que crea la lista
    @ManyToOne // Muchas listas pueden pertenecer a un solo usuario
    @JoinColumn(name = "usuario_id") // Nombre de la columna en la tabla
    private Usuario usuario;

    // Relación muchos a muchos con libros. Con @Builder.Default, se inicializa con HashSet.
    @ManyToMany
    @JoinTable(
        name = "lista_libros", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "lista_id"), // FK a esta entidad
        inverseJoinColumns = @JoinColumn(name = "libro_id") // FK al otro lado de la relación
    )
    @Builder.Default
    private Set<Libro> libros = new HashSet<>(); // Usamos HashSet para evitar duplicados
}