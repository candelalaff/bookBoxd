package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.ReseñaDto;
import com.proyecto.bookBoxd.dto.ReseñaCrearDto;
import com.proyecto.bookBoxd.mapper.ReseñaMapper;
import com.proyecto.bookBoxd.model.Reseña;
import com.proyecto.bookBoxd.service.ReseñaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reseñas") // Ruta base unificada para todos los endpoints de reseñas
public class ReseñaController {

    @Autowired
    private ReseñaService reseñaService;

    @Autowired
    private ReseñaMapper reseñaMapper;

    // Método para persistir una nueva reseña enviada desde el frontend.
    // Usamos @Valid para gatillar automáticamente las anotaciones de validación (como @Size y @DecimalMin)
    // declaradas en el DTO de entrada antes de que los datos toquen el servicio.
    @PostMapping
    public ResponseEntity<ReseñaDto> createReseña(@Valid @RequestBody ReseñaCrearDto reseñaCrearDto) {
        // Transformamos el DTO de entrada en la entidad del modelo mediante el mapper
        Reseña reseñaEntity = reseñaMapper.toEntity(reseñaCrearDto);
        
        // Delegamos la lógica de negocio e hidratación de relaciones al servicio y persistimos
        Reseña guardada = reseñaService.saveReseña(reseñaEntity); 
        
        // Retornamos un DTO de salida limpio para evitar problemas de recursividad cíclica en el JSON
        return ResponseEntity.ok(reseñaMapper.toDto(guardada));
    }

    // Endpoint para recuperar el catálogo completo de reseñas en formato plano (DTO)
    @GetMapping
    public ResponseEntity<List<ReseñaDto>> getAllReseñas() {
        List<Reseña> reseñas = reseñaService.findAllReseñas();
        
        // Mapeamos la lista de entidades a una lista de DTOs seguros para la capa de presentación
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

    // Endpoint para eliminar físicamente el registro de la reseña mediante su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReseña(@PathVariable Long id) {
        reseñaService.deleteReseña(id);
        
        // Respondemos con un estado 204 No Content que confirma el éxito de la operación sin cuerpo
        return ResponseEntity.noContent().build();
    }
}