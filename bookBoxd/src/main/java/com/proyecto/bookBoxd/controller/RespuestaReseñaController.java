package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.RespuestaReseñaDto;
import com.proyecto.bookBoxd.dto.RespuestaReseñaCrearDto;
import com.proyecto.bookBoxd.mapper.RespuestaReseñaMapper;
import com.proyecto.bookBoxd.model.RespuestaReseña;
import com.proyecto.bookBoxd.service.RespuestaReseñaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/respuestasreseñas")
public class RespuestaReseñaController {

    // Aquí le digo a Spring que me inyecte la instancia de mi servicio y de mi mapper.
    // Con `@Autowired`, Spring se encarga de darme una instancia de `RespuestaReseñaService`
    // y `RespuestaReseñaMapper` para que pueda usar sus métodos.
    @Autowired
    private RespuestaReseñaService respuestaReseñaService;

    @Autowired
    private RespuestaReseñaMapper respuestaMapper;

    // --- Endpoints de la API ---

    // Este es un endpoint `POST`.
    // Cuando recibo una solicitud a `/api/respuestasreseñas`, `PostMapping`
    // la captura y `@RequestBody` convierte el cuerpo de la solicitud JSON
    // en un objeto `RespuestaReseñaCrearDto`. Usamos `@Valid` para validar las restricciones del texto.
    @PostMapping
    public ResponseEntity<RespuestaReseñaDto> createRespuesta(@Valid @RequestBody RespuestaReseñaCrearDto crearDto) {
        // Convierto el DTO de entrada en la entidad del modelo.
        RespuestaReseña entidad = respuestaMapper.toEntity(crearDto);
        // Llamo a mi servicio para que se encargue de la lógica de guardar la respuesta.
        RespuestaReseña guardada = respuestaReseñaService.saveRespuestaReseña(entidad);
        // Devuelvo el DTO de salida mapeado de forma segura con un código 200 (OK).
        return ResponseEntity.ok(respuestaMapper.toDto(guardada));
    }
    
    // Este es un endpoint `GET`.
    // Cuando se hace una solicitud a `/api/respuestasreseñas`,
    // `GetMapping` me permite obtener una lista de todas las respuestas.
    @GetMapping
    public ResponseEntity<List<RespuestaReseñaDto>> getAllRespuestas() {
        // Le pido a mi servicio que me devuelva todas las respuestas.
        List<RespuestaReseña> respuestas = respuestaReseñaService.findAllRespuestas();
        // Las transformo en una lista de DTOs planos para no arrastrar relaciones cíclicas al Front.
        List<RespuestaReseñaDto> dtoList = respuestas.stream()
                .map(respuestaMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Este es otro endpoint `GET`, pero más específico.
    // El `{id}` en la ruta significa que espero un valor.
    // `@PathVariable` extrae ese valor y lo asigna a la variable `id`.
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaReseñaDto> getRespuestaById(@PathVariable Long id) {
        // Busco la respuesta por su ID.
        Optional<RespuestaReseña> respuesta = respuestaReseñaService.findById(id);
        // Si la encuentro, la mapeo a DTO y uso `.map(ResponseEntity::ok)` para devolver un 200 (OK).
        // Si no la encuentro, `.orElse(ResponseEntity.notFound().build())` devuelve un 404 (Not Found).
        return respuesta.map(respuestaMapper::toDto)
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