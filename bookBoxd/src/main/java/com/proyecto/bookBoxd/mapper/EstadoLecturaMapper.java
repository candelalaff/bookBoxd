package com.proyecto.bookBoxd.mapper;

import com.proyecto.bookBoxd.dto.EstadoLecturaDto;
import com.proyecto.bookBoxd.model.EstadoLectura;
import com.proyecto.bookBoxd.model.EstadoLecturaId;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.model.Libro;
import org.springframework.stereotype.Component;

@Component
public class EstadoLecturaMapper {

    // De Entidad a DTO seguro (Salida hacia Angular)
    public EstadoLecturaDto toDto(EstadoLectura entidad) {
        if (entidad == null) {
            return null;
        }
        EstadoLecturaDto dto = new EstadoLecturaDto();
        dto.setUsuarioId(entidad.getId().getUsuarioId());
        dto.setLibroId(entidad.getId().getLibroId());
        dto.setEstado(entidad.getEstado());
        return dto;
    }

    // De DTO a Entidad (Entrada desde el formulario de Angular)
    public EstadoLectura toEntity(EstadoLecturaDto dto) {
        if (dto == null) {
            return null;
        }
        
        // Creamos la clave compuesta embebida
        EstadoLecturaId idCompuesto = new EstadoLecturaId(dto.getUsuarioId(), dto.getLibroId());
        
        EstadoLectura entidad = new EstadoLectura();
        entidad.setId(idCompuesto);
        entidad.setEstado(dto.getEstado());

        // Instancias temporales para que el servicio resuelva las relaciones JPA
        Usuario usuario = new Usuario();
        usuario.setId(dto.getUsuarioId());
        entidad.setUsuario(usuario);

         Libro libro = new Libro();
        libro.setId(dto.getLibroId());
        entidad.setLibro(libro);

        return entidad;
    }
}