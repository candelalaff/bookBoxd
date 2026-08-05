package com.proyecto.bookBoxd.mapper;

import com.proyecto.bookBoxd.dto.LibroDto;
import com.proyecto.bookBoxd.dto.LibroCrearDto;
import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.model.Autor;
import com.proyecto.bookBoxd.model.Genero;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Componente Mapper encargado de la transformación bidireccional entre la entidad
 * de dominio Libro y sus respectivos DTOs (Data Transfer Objects).
 */
@Component
public class LibroMapper {

    /**
     * Mapea una entidad Libro a un DTO seguro (LibroDto) para enviar en las respuestas de la API.
     * 
     * Explicación del cambio:
     * Convertimos la constante del Enum Genero a String usando .name() para asegurar que 
     * la API exponga un formato de texto plano estándar compatible con JSON.
     */
    public LibroDto toDto(Libro libro) {
        if (libro == null) {
            return null;
        }

        LibroDto dto = new LibroDto();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setDescripcion(libro.getDescripcion());

        // Conversión explícita: Extraemos la representación en texto del Enum si no es nulo
        if (libro.getGenero() != null) {
            dto.setGenero(libro.getGenero().name());
        }

        if (libro.getAutores() != null) {
            Set<Long> ids = libro.getAutores().stream()
                    .map(Autor::getId)
                    .collect(Collectors.toSet());
            dto.setAutoresIds(ids);
        }

        return dto;
    }

    /**
     * Mapea un DTO de creación (LibroCrearDto) hacia la entidad Libro para su persistencia en BD.
     * 
     * Explicación del cambio:
     * El DTO recibe el género como un String desde el cliente. Usamos Genero.valueOf() para 
     * parsear ese texto y asignarlo al tipo fuertemente tipado Enum en la entidad. 
     * Incluimos un bloque try-catch para reasignar a Genero.OTROS si el texto enviado no coincide.
     */
    public Libro toEntity(LibroCrearDto dto) {
        if (dto == null) {
            return null;
        }

        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setDescripcion(dto.getDescripcion());

        // Conversión explícita: Mapeamos la cadena de texto recibida al tipo Enumerado
        if (dto.getGenero() != null) {
            try {
                libro.setGenero(Genero.valueOf(dto.getGenero()));
            } catch (IllegalArgumentException e) {
                // Mecanismo de resguardo (fallback) en caso de recibir un género no catalogado
                libro.setGenero(Genero.OTROS);
            }
        }

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