package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.model.EstadoLectura;
import com.proyecto.bookBoxd.service.EstadoLecturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/estadoslectura")
public class EstadoLecturaController {

    @Autowired
    private EstadoLecturaService estadoLecturaService;

    // Guarda o actualiza un estado de lectura.
    @PostMapping
    public EstadoLectura createEstadoLectura(@RequestBody EstadoLectura estadoLectura) {
        return estadoLecturaService.saveEstadoLectura(estadoLectura);
    }

    // Obtiene un estado de lectura específico.
    @GetMapping("/{usuarioId}/{libroId}")
    public ResponseEntity<EstadoLectura> getEstadoLectura(
            @PathVariable Long usuarioId, @PathVariable Long libroId) {
        return estadoLecturaService.findById(usuarioId, libroId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Elimina un estado de lectura.
    @DeleteMapping("/{usuarioId}/{libroId}")
    public ResponseEntity<Void> deleteEstadoLectura(
            @PathVariable Long usuarioId, @PathVariable Long libroId) {
        estadoLecturaService.deleteEstadoLectura(usuarioId, libroId);
        return ResponseEntity.noContent().build();
    }
}