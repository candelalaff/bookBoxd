package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.AutorDto;
import com.proyecto.bookBoxd.mapper.AutorMapper;
import com.proyecto.bookBoxd.model.Autor;
import com.proyecto.bookBoxd.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Autowired
    private AutorMapper autorMapper;

    // Crear o actualizar un autor (recibe DTO y devuelve DTO)
    @PostMapping
    public ResponseEntity<AutorDto> crearAutor(@Valid @RequestBody AutorDto autorDto) {
        Autor autorEntity = autorMapper.toEntity(autorDto);
        // Si tu servicio usa save o saveAutor, acomodá el nombre del método acá abajo
        Autor guardado = autorService.saveAutor(autorEntity); 
        return ResponseEntity.ok(autorMapper.toDto(guardado));
    }

    // Obtener un autor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<AutorDto> obtenerAutorPorId(@PathVariable Long id) {
        Optional<Autor> autor = autorService.findAutorById(id);
        return autor.map(autorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar todos los autores registrados
    @GetMapping
    public ResponseEntity<List<AutorDto>> listarAutores() {
        List<Autor> autores = autorService.findAllAutores();
        List<AutorDto> autoresDto = autores.stream()
                .map(autorMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(autoresDto);
    }

    // Eliminar un autor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }
}