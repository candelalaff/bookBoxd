package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.model.RespuestaReseña;
import com.proyecto.bookBoxd.service.RespuestaReseñaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestasreseñas")
public class RespuestaReseñaController {

    // Aquí le digo a Spring que me inyecte la instancia de mi servicio.
    // Con `@Autowired`, Spring se encarga de darme una instancia de `RespuestaReseñaService`
    // para que pueda usar sus métodos.
    @Autowired
    private RespuestaReseñaService respuestaReseñaService;

    // --- Endpoints de la API ---

    // Este es un endpoint `POST`.
    // Cuando recibo una solicitud a `/api/respuestasreseñas`, `PostMapping`
    // la captura y `@RequestBody` convierte el cuerpo de la solicitud JSON
    // en un objeto `RespuestaReseña`.
    @PostMapping
    public RespuestaReseña createRespuesta(@RequestBody RespuestaReseña respuesta) {
        // Llamo a mi servicio para que se encargue de la lógica de guardar la respuesta.
        return respuestaReseñaService.saveRespuestaReseña(respuesta);
    }
    
    // Este es un endpoint `GET`.
    // Cuando se hace una solicitud a `/api/respuestasreseñas`,
    // `GetMapping` me permite obtener una lista de todas las respuestas.
    @GetMapping
    public List<RespuestaReseña> getAllRespuestas() {
        // Le pido a mi servicio que me devuelva todas las respuestas y las devuelvo.
        return respuestaReseñaService.findAllRespuestas();
    }

    // Este es otro endpoint `GET`, pero más específico.
    // El `{id}` en la ruta significa que espero un valor.
    // `@PathVariable` extrae ese valor y lo asigna a la variable `id`.
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaReseña> getRespuestaById(@PathVariable Long id) {
        // Busco la respuesta por su ID.
        // Si la encuentro, uso `.map(ResponseEntity::ok)` para devolver un 200 (OK).
        // Si no la encuentro, `.orElse(ResponseEntity.notFound().build())` devuelve un 404 (Not Found).
        return respuestaReseñaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // endpoint DELETE para eliminar una respuesta.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRespuesta(@PathVariable Long id) {
        // Le pido a mi servicio que elimine la respuesta con ese ID.
        respuestaReseñaService.deleteRespuesta(id);
        // Devuelvo una respuesta 204 (No Content), que es el estándar para indicar
        // que el recurso se eliminó con éxito.
        return ResponseEntity.noContent().build();
    }
}