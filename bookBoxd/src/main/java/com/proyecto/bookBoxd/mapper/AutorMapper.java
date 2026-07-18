package com.proyecto.bookBoxd.mapper;

import com.proyecto.bookBoxd.dto.AutorDto;
import com.proyecto.bookBoxd.model.Autor;
import org.springframework.stereotype.Component;

@Component
public class AutorMapper {

    // De Entidad a DTO (para enviar al Front)
    public AutorDto toDto(Autor autor) {
        if (autor == null) {
            return null;
        }
        AutorDto dto = new AutorDto();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setBiografia(autor.getBiografia());
        return dto;
    }

    // De DTO a Entidad (para recibir del Front y guardar)
    public Autor toEntity(AutorDto dto) {
        if (dto == null) {
            return null;
        }
        Autor autor = new Autor();
        autor.setId(dto.getId());
        autor.setNombre(dto.getNombre());
        autor.setBiografia(dto.getBiografia());
        return autor;
    }
}