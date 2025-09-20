package com.proyecto.bookBoxd.model; // paquete donde se agrupan las entidades del modelo

// Importo las anotaciones necesarias
import jakarta.persistence.*;
import lombok.*;

@Entity // marco la clase como entidad JPA
@Getter // genera getters automáticamente
@Setter // genera setters automáticamente
@NoArgsConstructor // constructor vacío
@AllArgsConstructor // constructor completo
public class EstadoLectura {

    @EmbeddedId // esta es la clave compuesta embebida con usuarioId y libroId
    private EstadoLecturaId id;

    @ManyToOne
    @MapsId("usuarioId") //vincula el campo usuarioId del ID compuesto con esta relación
    @JoinColumn(name = "usuario_id") // columna en la tabla que referencia a Usuario
    private Usuario usuario;

    @ManyToOne
    @MapsId("libroId") // vincula el campo libroId del ID compuesto con esta relación
    @JoinColumn(name = "libro_id") // columna en la tabla que referencia a Libro
    private Libro libro;

    @Enumerated(EnumType.STRING) // guarda el estado (valor del enum) como texto en la bbd
    private Estado estado; // enum que representa el estado de lectura: LEIDO, LEYENDO, etc.

    public enum Estado {
        LEIDO, LEYENDO, POR_LEER, RELEIDO
    }
}
