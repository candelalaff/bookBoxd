package com.proyecto.bookBoxd.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.bookBoxd.dto.ReseñaCrearDto;
import com.proyecto.bookBoxd.dto.ReseñaDto;
import com.proyecto.bookBoxd.mapper.ReseñaMapper;
import com.proyecto.bookBoxd.model.Reseña;
import com.proyecto.bookBoxd.service.ReseñaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reseñas") // Ruta base unificada para todos los endpoints de reseñas
public class ReseñaController {

    @Autowired
    private ReseñaService reseñaService;

    @Autowired
    private ReseñaMapper reseñaMapper;

    // Metodo para persistir una nueva reseña vinculada al usuario autenticado mediante JWT
 // Usamos @Valid para gatillar automaticamente las anotaciones de validacion
    // declaradas en el DTO de entrada antes de que los datos toquen el servicio.
    @PostMapping
    public ResponseEntity<ReseñaDto> createReseña(@Valid @RequestBody ReseñaCrearDto reseñaCrearDto) {
        // Transformamos el DTO de entrada en la entidad del modelo mediante el mapper
        Reseña reseñaEntity = reseñaMapper.toEntity(reseñaCrearDto);
        
        // Delegamos la logica de negocio al servicio y persistimos
        Reseña guardada = reseñaService.saveReseña(reseñaEntity); 
        
        // Retornamos un DTO de salida limpio con respuesta HTTP 201 Created
        return new ResponseEntity<>(reseñaMapper.toDto(guardada), HttpStatus.CREATED);
    }

    // Endpoint para recuperar el catalogo completo de reseñas en formato plano (DTO)
    @GetMapping
    public ResponseEntity<List<ReseñaDto>> getAllReseñas() {
        List<Reseña> reseñas = reseñaService.findAllReseñas();
        
        // Mapeamos la lista de entidades a una lista de DTOs seguros para la capa de presentacion
        List<ReseñaDto> reseñasDto = reseñas.stream()
                .map(reseñaMapper::toDto)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(reseñasDto);
    }

    // Endpoint para buscar una reseña puntual por su clave primaria
    @GetMapping("/{id}")
    public ResponseEntity<ReseñaDto> getReseñaById(@PathVariable Long id) {
        Optional<Reseña> reseña = reseñaService.findReseñaById(id);
        
        // Si el Optional contiene la entidad, la mapeamos a DTO y respondemos 200 OK.
        // Si no se encuentra, devolvemos un estado 404 Not Found de forma limpia.
        return reseña.map(reseñaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para eliminar fisicamente el registro de la reseña mediante su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReseña(@PathVariable Long id) {
        reseñaService.deleteReseña(id);
        
        // Respondemos con un estado 204 No Content que confirma el exito de la operacion sin cuerpo
        return ResponseEntity.noContent().build();
    }
}