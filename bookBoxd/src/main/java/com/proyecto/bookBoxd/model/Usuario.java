package com.proyecto.bookBoxd.model;

import jakarta.persistence.*; // Anotaciones JPA para mapeo ORM
import jakarta.validation.constraints.Email; // Para validar formato de email
import jakarta.validation.constraints.NotBlank; // Para validar que el campo no esté vacío
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size; // Para validar el tamaño de un campo
import lombok.*; // Anotaciones de Lombok

import java.util.Set;
import java.util.HashSet;


@Entity // Indica que esta clase es una entidad JPA
@Data // Lombok: genera getters, setters, equals, hashCode y toString
@NoArgsConstructor // Lombok: genera constructor sin argumentos
@AllArgsConstructor // Lombok: genera constructor con todos los campos
@Builder // Lombok: implementa el patrón de diseño Builder
public class Usuario {


    @Id // id es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // configura la generacion de valores para la clave primaria
    private Long id;
    
    // entidad alias
    @Column(unique = true) // Asegura que el alias sea único en la base de datos
    @NotBlank(message = "El alias no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El alias debe tener entre 3 y 50 caracteres.")
    private String alias;

    // Validación del nombre:
    @NotBlank(message = "El nombre no puede estar vacío") // - No puede estar en blanco
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres") // - Máximo 100 caracteres
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")  // - Solo letras y espacios (con tildes y ñ incluidas)
    private String nombre;

     // Validación del apellido (mismas que nombre)
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 100, message = "El apellido no puede tener más de 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido solo puede contener letras y espacios")
    private String apellido;

    // Validación del email:
   
    @Column(unique = true)  // - No puede repetirse (restricción en base de datos)
    @NotBlank(message = "El email no puede estar vacío") // - No puede estar vacío
    @Email(message = "Ingrese un formato de email válido") // - Debe tener formato válido (ej: persona@dominio.com) 
    private String email;


    // Validación de la contraseña
    @NotBlank(message = "La contraseña no puede estar vacía")  // - No puede estar vacía
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")   // - Debe tener al menos 6 caracteres
    private String password;
    
    // Por defecto, el perfil del usuario es público (false)
    @Builder.Default
    private boolean perfilPrivado = false;

     
    // Relación uno a muchos con listas personalizadas (el usuario tiene muchas listas)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ListaPersonalizada> listas = new HashSet<>();

    // Relación uno a muchos con reseñas (el usuario tiene muchas reseñas)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Reseña> reseñas = new HashSet<>();
    
    // Relación Muchos a Muchos con Libro (Libros leídos)
    // Se usa una tabla de unión para almacenar los estados de lectura
    @ManyToMany
    @JoinTable(
        name = "usuario_libros_leidos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    @Builder.Default
    private Set<Libro> librosLeidos = new HashSet<>();

    // Relación Uno a Muchos para EstadoLectura
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<EstadoLectura> estadosLectura = new HashSet<>();
}