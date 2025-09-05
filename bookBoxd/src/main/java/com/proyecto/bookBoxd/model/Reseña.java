package com.proyecto.bookBoxd.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    //relación con Libro
    @ManyToOne
    @JoinColumn(name = "libro_id") //nombre de la columna de clave foránea
    private Libro libro;
}
