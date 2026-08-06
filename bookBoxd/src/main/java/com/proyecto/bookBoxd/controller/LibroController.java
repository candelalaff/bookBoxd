package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.LibroDto;
import com.proyecto.bookBoxd.dto.LibroCrearDto;
import com.proyecto.bookBoxd.mapper.LibroMapper;
import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.proyecto.bookBoxd.model.Genero;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Controlador REST encargado de exponer las rutas para la gestion del catalogo de libros
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private LibroMapper libroMapper;

    // Crea un nuevo libro usando el DTO de entrada y activando las validaciones
    // Solo permitido para usuarios con rol administrador
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibroDto> createLibro(@Valid @RequestBody LibroCrearDto libroCrearDto) { 
        Libro libroEntity = libroMapper.toEntity(libroCrearDto);
        Libro guardado = libroService.saveLibro(libroEntity);
        return new ResponseEntity<>(libroMapper.toDto(guardado), HttpStatus.CREATED);
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

    // Actualiza los datos de un libro existente por su ID
    // Solo permitido para usuarios con rol administrador
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibroDto> updateLibro(@PathVariable Long id, @Valid @RequestBody LibroCrearDto libroCrearDto) {
        Optional<Libro> libroExistente = libroService.findLibroById(id);
        if (libroExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Libro libroActualizar = libroMapper.toEntity(libroCrearDto);
        libroActualizar.setId(id); // Mantengo el mismo ID para actualizar
        Libro actualizado = libroService.saveLibro(libroActualizar);
        
        return ResponseEntity.ok(libroMapper.toDto(actualizado));
    }

    // Mapea los DELETE para eliminar un libro por su ID
    // Solo permitido para usuarios con rol administrador
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint REST para obtener el catalogo completo de generos
    @GetMapping("/generos")
    public ResponseEntity<Genero[]> obtenerGeneros() {
        return ResponseEntity.ok(Genero.values());
    }
}