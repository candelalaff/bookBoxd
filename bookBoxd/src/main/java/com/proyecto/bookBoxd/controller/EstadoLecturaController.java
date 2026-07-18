package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.EstadoLecturaDto;
import com.proyecto.bookBoxd.mapper.EstadoLecturaMapper;
import com.proyecto.bookBoxd.model.EstadoLectura;
import com.proyecto.bookBoxd.service.EstadoLecturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/estados-lectura") // Ruta unificada para gestionar los estados de los libros
public class EstadoLecturaController {

    @Autowired
    private EstadoLecturaService estadoLecturaService;

    @Autowired
    private EstadoLecturaMapper estadoLecturaMapper;

    // Método para guardar o actualizar el estado. 
    // Captura el DTO, lo valida mediante @Valid y delega el guardado al servicio.
    @PostMapping
    public ResponseEntity<EstadoLecturaDto> guardarEstado(@Valid @RequestBody EstadoLecturaDto dto) {
        EstadoLectura entidad = estadoLecturaMapper.toEntity(dto);
        EstadoLectura guardado = estadoLecturaService.saveEstadoLectura(entidad);
        return ResponseEntity.ok(estadoLecturaMapper.toDto(guardado));
    }

    // Endpoint de búsqueda basado exactamente en la firma de mi servicio.
    // Recibe ambos IDs por la URL, delega en findById y mapea el Optional resultante a DTO.
    @GetMapping("/usuario/{usuarioId}/libro/{libroId}")
    public ResponseEntity<EstadoLecturaDto> obtenerEstado(
            @PathVariable Long usuarioId, 
            @PathVariable Long libroId) {
        
        Optional<EstadoLectura> estado = estadoLecturaService.findById(usuarioId, libroId);
        return estado.map(estadoLecturaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para eliminar un registro. 
    // Instancia los IDs dinámicos de la URL y ejecuta el método deleteEstadoLectura de la capa de servicio.
    @DeleteMapping("/usuario/{usuarioId}/libro/{libroId}")
    public ResponseEntity<Void> eliminarEstado(
            @PathVariable Long usuarioId, 
            @PathVariable Long libroId) {
        
        estadoLecturaService.deleteEstadoLectura(usuarioId, libroId);
        return ResponseEntity.noContent().build();
    }
}