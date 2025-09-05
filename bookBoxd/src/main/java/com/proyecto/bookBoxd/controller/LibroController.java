package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/libros") //ruta base para todos los endpoints de este controlador.

public class LibroController {
	//inyecta la instancia de libroService ->logica de negocio se encarga la capa de servicio
	@Autowired
    private LibroService libroService;
	
	@PostMapping
    public Libro createLibro(@RequestBody Libro libro) { //toma el cuerpo JSON de la solicitud
		//y lo convierte en un objeto Libro.
        return libroService.saveLibro(libro);
    }
	
	@GetMapping
    public List<Libro> getAllLibros() { //mapea los get para obtener todos los libros
        return libroService.findAllLibros();
    }
	
	@GetMapping("/{id}")// mapea los get que tiene un id en la url
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) { //Extrae el ID de la URL.
        return libroService.findLibroById(id)
                // Si el libro se encuentra, devuelve una respuesta HTTP 200 (OK) con el objeto del libro en el cuerpo.
                .map(ResponseEntity::ok)
                //  Si el libro no se encuentra, devuelve HTTP 404 (Not Found).
                .orElse(ResponseEntity.notFound().build());
    }
	
	// Mapea los DELETE para eliminar un libro por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        // Devuelve una respuesta HTTP 204 (No Content) -> la eliminación fue exitosa sin cuerpo de respuesta.
        return ResponseEntity.noContent().build();
    }

}
