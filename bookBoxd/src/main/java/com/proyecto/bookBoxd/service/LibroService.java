package com.proyecto.bookBoxd.service;

import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.model.Autor;
import com.proyecto.bookBoxd.repository.LibroRepository;
import com.proyecto.bookBoxd.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LibroService {

    // @Autowired: Inyecta automáticamente la instancia de LibroRepository
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired // para gestionar la relación entre libros y autores antes de guardar un libro.
    private AutorRepository autorRepository;

    // Método para guardar un nuevo libro. maneja la relación @ManyToMany con la entidad Autor.
    public Libro saveLibro(Libro libro) {
        // el objeto Libro llega con los objetos Autor asociados.
        // los autores ya existan en la base de datos y si son nuevps que se guarden.
        Set<Autor> autoresExistentes = libro.getAutores().stream()
           // solo busca los autores existentes, sin intentar guardarlos de nuevo.
           .map(autor -> autorRepository.findById(autor.getId())
        		   .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + autor.getId())))
           .collect(Collectors.toSet());
            
         libro.setAutores(autoresExistentes);
        
        // guardamos el libro, con sus relaciones a autores.
        return libroRepository.save(libro);
    }

    // Método para obtener todos los libros.
    public List<Libro> findAllLibros() {
        return libroRepository.findAll();
    }

    // Método para buscar un libro por su ID.
    public Optional<Libro> findLibroById(Long id) {
        return libroRepository.findById(id);
    }
    
    // Método para eliminar un libro por su ID.
    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }
}