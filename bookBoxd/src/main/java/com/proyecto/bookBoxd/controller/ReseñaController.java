package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.model.Reseña;
import com.proyecto.bookBoxd.service.ReseñaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reseñas")
public class ReseñaController {

    @Autowired
    private ReseñaService reseñaService;

    // Mapea las solicitudes HTTP POST para crear una nueva reseña.
    // El cuerpo de la solicitud JSON debe incluir los IDs del usuario y el libro.
    @PostMapping
    public Reseña createReseña(@RequestBody Reseña reseña) {
        return reseñaService.saveReseña(reseña);
    }

    // Mapea las solicitudes HTTP GET para obtener todas las reseñas.
    @GetMapping
    public List<Reseña> getAllReseñas() {
        return reseñaService.findAllReseñas();
    }

    // Mapea las solicitudes HTTP GET para obtener una reseña por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Reseña> getReseñaById(@PathVariable Long id) {
        return reseñaService.findReseñaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mapea las solicitudes HTTP DELETE para eliminar una reseña por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReseña(@PathVariable Long id) {
        reseñaService.deleteReseña(id);
        return ResponseEntity.noContent().build();
    }
}