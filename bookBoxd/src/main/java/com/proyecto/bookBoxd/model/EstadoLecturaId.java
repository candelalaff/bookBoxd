package com.proyecto.bookBoxd.model; // paquete donde se agrupan las entidades del modelo

// Importo las anotaciones necesarias de JPA y Lombok
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable // esta clase se puede embebir como clave compuesta en otra entidad
@Getter // genera automáticamente los métodos get
@Setter // genera automáticamente los métodos set
@NoArgsConstructor // constructor vacío requerido por JPA
@AllArgsConstructor // constructor con todos los campos
@EqualsAndHashCode // necesario para claves compuestas, asegura comparación por contenido
public class EstadoLecturaId implements Serializable {
	
	 private static final long serialVersionUID = 1L; // identificador de versión para serialización
	
    private Long usuarioId; // referencia al ID del usuario
    private Long libroId;   // referencia al ID del libro
}
