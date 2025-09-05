package com.proyecto.bookBoxd.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaReseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
