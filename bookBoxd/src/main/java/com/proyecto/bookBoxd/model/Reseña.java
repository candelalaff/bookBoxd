package com.proyecto.bookBoxd.model;

import jakarta.persistence.*; // Anotaciones de JPA
import jakarta.validation.constraints.*; // Anotaciones para validación de datos
import lombok.AllArgsConstructor; // Se importa AllArgsConstructor
import lombok.Data; // Se importa Data, que agrupa Getter, Setter, etc.
import lombok.NoArgsConstructor; // Se importa NoArgsConstructor
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

// @Entity: se mapeará a una tabla de base de datos
@Entity
@Data
// @NoArgsConstructor: constructor sin argumentos, necesario para JPA
@NoArgsConstructor
// @AllArgsConstructor: constructor con todos los argumentos, para crear objetos
@AllArgsConstructor
public class Reseña {
    
    @Id // id --> clave primaria de la tabla
    // @GeneratedValue: usa una columna autoincremental p/ generar automaticamente el valor del id.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank: Asegura que el cuerpo de la reseña no sea nulo ni vacío.
    @NotBlank(message = "El cuerpo de la reseña no puede estar vacío.")
    @Size(min = 1, max = 500, message = "La reseña debe tener entre 1 y 500 caracteres.")
    private String cuerpo;

    //se pasan los valores como string a decimalMIn y decimalmax
    @DecimalMin(value = "0.5", message = "La calificación debe ser al menos 0.5.")
    @DecimalMax(value = "5.0", message = "La calificación no puede ser mayor a 5.")
    private Double calificacion;

    // Relación con Usuario @ManyToOne: muchas reseñas pueden pertenecer a un solo usuario.
    // @JoinColumn: indica el nombre de la columna de clave foránea en la tabla "reseña"
    // que se relaciona con la clave primaria de la tabla "usuario".
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    // Relación con Libro @ManyToOne: muchas reseñas pueden pertenecer a un libro.
    // @JoinColumn: indica el nombre de la columna de clave foránea en la tabla "reseña"
    // que se relaciona con la clave primaria de la tabla "libro".
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;
    
 // Método de validación personalizado. Se ejecuta automáticamente al intentar guardar la entidad.
    // La anotación @AssertTrue espera que el método devuelva 'true'.
    @AssertTrue(message = "La calificación debe ser un múltiplo de 0.5 (ej: 1.0, 1.5, 2.0).")
    private boolean isCalificacionValida() {
        // Esta es la clave de la validación.
        // Multiplicamos la calificación por 2 y comprobamos si el residuo al dividir por 1 es 0.
        // Por ejemplo, si calificacion es 2.5, (2.5 * 2) = 5.0. 5.0 % 1 = 0.
        // Si calificacion es 2.64, (2.64 * 2) = 5.28. 5.28 % 1 = 0.28, que no es 0.
        if (calificacion == null) {
            return false;
        }
        return (calificacion * 2) % 1 == 0;
    }
}