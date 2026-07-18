package com.proyecto.bookBoxd.mapper;

import com.proyecto.bookBoxd.dto.LibroDto;
import com.proyecto.bookBoxd.dto.LibroCrearDto;
import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.model.Autor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LibroMapper {

    // Convertir de Entidad a DTO seguro
    public LibroDto toDto(Libro libro) {
        if (libro == null) {
            return null;
        }

        LibroDto dto = new LibroDto();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setDescripcion(libro.getDescripcion());
        dto.setGenero(libro.getGenero());

        if (libro.getAutores() != null) {
            Set<Long> ids = libro.getAutores().stream()
                    .map(Autor::getId)
                    .collect(Collectors.toSet());
            dto.setAutoresIds(ids);
        }

        return dto;
    }

    // Convertir de DTO de entrada a Entidad para persistir
    public Libro toEntity(LibroCrearDto dto) {
        if (dto == null) {
            return null;
        }

        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setDescripcion(dto.getDescripcion());
        libro.setGenero(dto.getGenero());

        if (dto.getAutoresIds() != null) {
            Set<Autor> autores = dto.getAutoresIds().stream()
                    .map(id -> {
                        Autor autor = new Autor();
                        autor.setId(id);
                        return autor;
                    })
                    .collect(Collectors.toSet());
            libro.setAutores(autores);
        }

        return libro;
    }
}