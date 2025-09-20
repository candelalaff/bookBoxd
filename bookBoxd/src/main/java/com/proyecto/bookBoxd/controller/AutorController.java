package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.model.Autor; // Importa la entidad Autor
import com.proyecto.bookBoxd.service.AutorService; // Importa el servicio
import org.springframework.beans.factory.annotation.Autowired; // Anotación para inyección de dependencias
import org.springframework.http.ResponseEntity; // Clase para construir respuestas HTTP completas
import org.springframework.web.bind.annotation.*; // Anotaciones para los endpoints REST

import java.util.List;

// controlador que devuelve directamente datos JSON/XML no vistas HTML. 
@RestController
// define URL base para todos los métodos de este controlador -> las rutas comenzarán con /api/autores.
@RequestMapping("/api/autores")
public class AutorController {

    // @Autowired inyecta la instancia de AutorService para usar sus métodos
    @Autowired
    private AutorService autorService;

    // @PostMapping mapea las solicitudes HTTP POST a este método.
    // @RequestBody toma el JSON del cuerpo de la solicitud y lo convierte en un objeto Autor.
    @PostMapping
    public Autor createAutor(@RequestBody Autor autor) {
        return autorService.saveAutor(autor);
    }

    // @GetMapping mapea las solicitudes HTTP GET a este método.
    @GetMapping
    public List<Autor> getAllAutores() {
        return autorService.findAllAutores();
    }

    // ResponseEntity permite controlar la respuesta, incluyendo el código de estado.
    @GetMapping("/{id}") //mapea las solicitudes GET que incluyen un ID en la URL
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id) { // @PathVariable extrae el ID de la URL.
        // El servicio devuelve un Optional -> permite manejar la ausencia de un autor.
        return autorService.findAutorById(id)
                .map(ResponseEntity::ok) // Si el autor existe (map), devuelve respuesta 200 OK con el autor.
                .orElse(ResponseEntity.notFound().build()); // Si no existe (orElse), devuelve respuesta 404 Not Found.
    }

    // @DeleteMapping("/{id}") mapea las solicitudes HTTP DELETE para eliminar un autor.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        // Llama al servicio para eliminar el autor por ID.
        autorService.deleteAutor(id);
        // Devuelve una respuesta 204 No Content, que indica éxito sin cuerpo de respuesta.
        return ResponseEntity.noContent().build();
    }
}