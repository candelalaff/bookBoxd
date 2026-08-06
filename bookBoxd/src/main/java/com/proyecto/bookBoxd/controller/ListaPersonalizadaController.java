package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.ListaPersonalizadaDto;
import com.proyecto.bookBoxd.dto.ListaPersonalizadaCrearDto;
import com.proyecto.bookBoxd.mapper.ListaPersonalizadaMapper;
import com.proyecto.bookBoxd.model.ListaPersonalizada;
import com.proyecto.bookBoxd.service.ListaPersonalizadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
// Mapea todas las solicitudes que comienzan con /api/listas a este controlador
@RequestMapping("/api/listas")
public class ListaPersonalizadaController {

    // Inyeccion de dependencias: Spring crea una instancia de ListaPersonalizadaService
    // y la inyecta aca automaticamente -> permite que el controlador use los metodos service
    @Autowired
    private ListaPersonalizadaService listaPersonalizadaService;

    // Inyeccion del Mapper para transformar los DTOs planos hacia la capa de presentacion y viceversa
    @Autowired
    private ListaPersonalizadaMapper listaMapper;

    // --- Metodos de Lectura (GET) ---

    // Mapea las solicitudes HTTP GET a la URL base /api/listas
    // Devuelve una lista de todas las listas personalizadas convertidas en DTOs seguros
    @GetMapping
    public ResponseEntity<List<ListaPersonalizadaDto>> getAllListas() {
        List<ListaPersonalizada> listas = listaPersonalizadaService.findAllListas();
        List<ListaPersonalizadaDto> dtoList = listas.stream()
                .map(listaMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Mapea las solicitudes HTTP GET a /api/listas/{id}
    // El @PathVariable extrae el 'id' de la URL
    @GetMapping("/{id}")
    public ResponseEntity<ListaPersonalizadaDto> getListaById(@PathVariable Long id) {
        // Busca una lista por ID. Si la encuentra, la convierte a DTO y devuelve una respuesta HTTP 200 (OK)
        // con el cuerpo de la lista. Si no la encuentra, devuelve un 404 (Not Found)
        Optional<ListaPersonalizada> lista = listaPersonalizadaService.findListaById(id);
        return lista.map(listaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Metodos de Escritura (POST, PUT, DELETE) ---

    // Mapea las solicitudes HTTP POST a /api/listas para crear una nueva lista
    // El @RequestBody convierte el JSON de la solicitud en un objeto ListaPersonalizadaCrearDto
    // Usamos @Valid para que Spring valide automaticamente los campos obligatorios en el DTO de entrada
    @PostMapping
    public ResponseEntity<ListaPersonalizadaDto> createLista(@Valid @RequestBody ListaPersonalizadaCrearDto crearDto) {
        ListaPersonalizada entidad = listaMapper.toEntity(crearDto);
        ListaPersonalizada guardada = listaPersonalizadaService.saveListaPersonalizada(entidad);
        return new ResponseEntity<>(listaMapper.toDto(guardada), HttpStatus.CREATED);
    }

    // Mapea las solicitudes HTTP PUT para actualizar o anadir recursos
    // Este endpoint anade un libro a una lista existente
    // Ambos IDs se extraen de la URL con @PathVariable
    @PutMapping("/{listaId}/libros/{libroId}")
    public ResponseEntity<ListaPersonalizadaDto> addLibroToLista(
            @PathVariable Long listaId, @PathVariable Long libroId) {
        try {
            // El try-catch es para manejar la excepcion si la lista o el libro no existen
            ListaPersonalizada lista = listaPersonalizadaService.addLibroToLista(listaId, libroId);
            return ResponseEntity.ok(listaMapper.toDto(lista)); // Si es exitoso, mapea a DTO y devuelve 200 (OK)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si falla (no se encuentra la lista o el libro), devuelve 404
        }
    }

    // Mapea las solicitudes HTTP DELETE para eliminar una lista por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        listaPersonalizadaService.deleteLista(id);
        // Devuelve una respuesta 204 (No Content), que es el estandar para
        // operaciones de borrado exitosas sin cuerpo de respuesta
        return ResponseEntity.noContent().build();
    }
}