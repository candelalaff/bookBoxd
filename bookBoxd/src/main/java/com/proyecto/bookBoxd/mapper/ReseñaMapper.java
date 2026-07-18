package com.proyecto.bookBoxd.mapper;

import com.proyecto.bookBoxd.dto.ReseñaDto;
import com.proyecto.bookBoxd.dto.ReseñaCrearDto;
import com.proyecto.bookBoxd.model.Reseña;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.model.Libro;
import org.springframework.stereotype.Component;

@Component
public class ReseñaMapper {

    // De Entidad a DTO seguro
    public ReseñaDto toDto(Reseña reseña) {
        if (reseña == null) {
            return null;
        }

        ReseñaDto dto = new ReseñaDto();
        dto.setId(reseña.getId());
        dto.setCuerpo(reseña.getCuerpo());
        dto.setCalificacion(reseña.getCalificacion());
        
        if (reseña.getUsuario() != null) {
            dto.setUsuarioId(reseña.getUsuario().getId());
        }
        if (reseña.getLibro() != null) {
            dto.setLibroId(reseña.getLibro().getId());
        }

        return dto;
    }

    // De DTO de creación a Entidad
    public Reseña toEntity(ReseñaCrearDto dto) {
        if (dto == null) {
            return null;
        }

        Reseña reseña = new Reseña();
        reseña.setCuerpo(dto.getCuerpo());
        reseña.setCalificacion(dto.getCalificacion());

        // Seteamos proxies o instancias vacías con el ID.
        // El servicio correspondiente se encargará de validar si existen al persistir.
        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            reseña.setUsuario(usuario);
        }

        if (dto.getLibroId() != null) {
            Libro libro = new Libro();
            libro.setId(dto.getLibroId());
            reseña.setLibro(libro);
        }

        return reseña;
    }
}
