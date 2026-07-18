package com.proyecto.bookBoxd.mapper;

import com.proyecto.bookBoxd.dto.RespuestaReseñaDto;
import com.proyecto.bookBoxd.dto.RespuestaReseñaCrearDto;
import com.proyecto.bookBoxd.model.RespuestaReseña;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.model.Reseña;
import org.springframework.stereotype.Component;

@Component
public class RespuestaReseñaMapper {

    // Transformo de Entidad a DTO plano para enviar al Front
    public RespuestaReseñaDto toDto(RespuestaReseña respuesta) {
        if (respuesta == null) {
            return null;
        }

        RespuestaReseñaDto dto = new RespuestaReseñaDto();
        dto.setId(respuesta.getId());
        dto.setContenido(respuesta.getContenido());
        dto.setFechaCreacion(respuesta.getFechaCreacion()); // Hibernate genera esto automáticamente
        
        if (respuesta.getUsuario() != null) {
            dto.setUsuarioId(respuesta.getUsuario().getId());
        }
        if (respuesta.getReseña() != null) {
            dto.setReseñaId(respuesta.getReseña().getId());
        }

        return dto;
    }

    // Transformo de DTO de entrada a Entidad para persistir
    public RespuestaReseña toEntity(RespuestaReseñaCrearDto dto) {
        if (dto == null) {
            return null;
        }

        RespuestaReseña respuesta = new RespuestaReseña();
        respuesta.setContenido(dto.getContenido());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            respuesta.setUsuario(usuario);
        }

        if (dto.getReseñaId() != null) {
            Reseña reseña = new Reseña();
            reseña.setId(dto.getReseñaId());
            respuesta.setReseña(reseña);
        }

        return respuesta;
    }
}