package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.LibroDto;
import com.proyecto.bookBoxd.dto.LibroCrearDto;
import com.proyecto.bookBoxd.mapper.LibroMapper;
import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libros") // ruta base para todos los endpoints de este controlador.
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private LibroMapper libroMapper;
	
    // Crea un nuevo libro usando el DTO de entrada y activando las validaciones
    @PostMapping
    public ResponseEntity<LibroDto> createLibro(@Valid @RequestBody LibroCrearDto libroCrearDto) { 
        Libro libroEntity = libroMapper.toEntity(libroCrearDto);
        Libro guardado = libroService.saveLibro(libroEntity);
        return ResponseEntity.ok(libroMapper.toDto(guardado));
    }
	
    // Mapea los GET para obtener todos los libros convertidos en DTOs seguros
    @GetMapping
    public ResponseEntity<List<LibroDto>> getAllLibros() { 
        List<Libro> libros = libroService.findAllLibros();
        List<LibroDto> librosDto = libros.stream()
                .map(libroMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(librosDto);
    }
	
    // Mapea los GET que tienen un ID en la URL
    @GetMapping("/{id}")
    public ResponseEntity<LibroDto> getLibroById(@PathVariable Long id) { 
        Optional<Libro> libro = libroService.findLibroById(id);
        return libro.map(libroMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
    // Mapea los DELETE para eliminar un libro por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }
}