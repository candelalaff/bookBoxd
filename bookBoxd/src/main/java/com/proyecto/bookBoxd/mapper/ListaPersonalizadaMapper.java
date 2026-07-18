package com.proyecto.bookBoxd.mapper;

import com.proyecto.bookBoxd.dto.ListaPersonalizadaDto;
import com.proyecto.bookBoxd.dto.ListaPersonalizadaCrearDto;
import com.proyecto.bookBoxd.model.ListaPersonalizada;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.model.Libro;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ListaPersonalizadaMapper {

    // Traduzco de Entidad a DTO seguro para enviar a Angular
    public ListaPersonalizadaDto toDto(ListaPersonalizada lista) {
        if (lista == null) {
            return null;
        }

        ListaPersonalizadaDto dto = new ListaPersonalizadaDto();
        dto.setId(lista.getId());
        dto.setNombre(lista.getNombre());
        dto.setDescription(lista.getDescripcion());
        dto.setEsPublica(lista.isEsPublica());

        if (lista.getUsuario() != null) {
            dto.setUsuarioId(lista.getUsuario().getId());
        }

        if (lista.getLibros() != null) {
            Set<Long> ids = lista.getLibros().stream()
                    .map(Libro::getId)
                    .collect(Collectors.toSet());
            dto.setLibrosIds(ids);
        }

        return dto;
    }

    // Traduzco de DTO de entrada a Entidad mapeable por JPA
    public ListaPersonalizada toEntity(ListaPersonalizadaCrearDto dto) {
        if (dto == null) {
            return null;
        }

        ListaPersonalizada lista = new ListaPersonalizada();
        lista.setNombre(dto.getNombre());
        lista.setDescripcion(dto.getDescription());
        lista.setEsPublica(dto.isEsPublica());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            lista.setUsuario(usuario);
        }

        // CORRECCIÓN: Instanciamos los libros usando la variable correcta en el map
        if (dto.getLibrosIds() != null) {
            Set<Libro> libros = dto.getLibrosIds().stream()
                    .map(id -> {
                        Libro libro = new Libro();
                        libro.setId(id);
                        return libro; // Antes decía 'return r;' por error
                    })
                    .collect(Collectors.toSet());
            lista.setLibros(libros);
        }

        return lista;
    }
}