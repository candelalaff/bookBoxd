package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.ListaPersonalizadaDto;
import com.proyecto.bookBoxd.dto.ListaPersonalizadaCrearDto;
import com.proyecto.bookBoxd.mapper.ListaPersonalizadaMapper;
import com.proyecto.bookBoxd.model.ListaPersonalizada;
import com.proyecto.bookBoxd.service.ListaPersonalizadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
// Mapea todas las solicitudes que comienzan con /api/listas a este controlador.
@RequestMapping("/api/listas")
public class ListaPersonalizadaController {

    // Inyección de dependencias: Spring crea una instancia de ListaPersonalizadaService
    // y la inyecta acá automáticamente -> permite que el controlador use los métodos service.
    @Autowired
    private ListaPersonalizadaService listaPersonalizadaService;

    // Inyección del Mapper para transformar los DTOs planos hacia la capa de presentación y viceversa.
    @Autowired
    private ListaPersonalizadaMapper listaMapper;

    // --- Métodos de Lectura (GET) ---
    
    // Mapea las solicitudes HTTP GET a la URL base /api/listas.
    // Devuelve una lista de todas las listas personalizadas convertidas en DTOs seguros.
    @GetMapping
    public ResponseEntity<List<ListaPersonalizadaDto>> getAllListas() {
        List<ListaPersonalizada> listas = listaPersonalizadaService.findAllListas();
        List<ListaPersonalizadaDto> dtoList = listas.stream()
                .map(listaMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Mapea las solicitudes HTTP GET a /api/listas/{id}.
    // El @PathVariable extrae el 'id' de la URL.
    @GetMapping("/{id}")
    public ResponseEntity<ListaPersonalizadaDto> getListaById(@PathVariable Long id) {
        // Busca una lista por ID. Si la encuentra, la convierte a DTO y devuelve una respuesta HTTP 200 (OK)
        // con el cuerpo de la lista. Si no la encuentra, devuelve un 404 (Not Found).
        Optional<ListaPersonalizada> lista = listaPersonalizadaService.findListaById(id);
        return lista.map(listaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Métodos de Escritura (POST, PUT, DELETE) ---

    // Mapea las solicitudes HTTP POST a /api/listas para crear una nueva lista.
    // El @RequestBody convierte el JSON de la solicitud en un objeto ListaPersonalizadaCrearDto.
    // Usamos @Valid para que Spring valide automáticamente los campos obligatorios en el DTO de entrada.
    @PostMapping
    public ResponseEntity<ListaPersonalizadaDto> createLista(@Valid @RequestBody ListaPersonalizadaCrearDto crearDto) {
        ListaPersonalizada entidad = listaMapper.toEntity(crearDto);
        ListaPersonalizada guardada = listaPersonalizadaService.saveListaPersonalizada(entidad);
        return ResponseEntity.ok(listaMapper.toDto(guardada));
    }
    
    // Mapea las solicitudes HTTP PUT para actualizar o añadir recursos.
    // Este endpoint añade un libro a una lista existente.
    // Ambos IDs se extraen de la URL con @PathVariable.
    @PutMapping("/{listaId}/libros/{libroId}")
    public ResponseEntity<ListaPersonalizadaDto> addLibroToLista(
            @PathVariable Long listaId, @PathVariable Long libroId) {
        try {
            // El try-catch es para manejar la excepción si la lista o el libro no existen.
            ListaPersonalizada lista = listaPersonalizadaService.addLibroToLista(listaId, libroId);
            return ResponseEntity.ok(listaMapper.toDto(lista)); // Si es exitoso, mapea a DTO y devuelve 200 (OK) con la lista actualizada.
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si falla (no se encuentra la lista o el libro), devuelve 404.
        }
    }
    
    // Mapea las solicitudes HTTP DELETE para eliminar una lista por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        listaPersonalizadaService.deleteLista(id);
        // Devuelve una respuesta 204 (No Content), que es el estándar para
        // operaciones de borrado exitosas sin cuerpo de respuesta.
        return ResponseEntity.noContent().build();
    }
}