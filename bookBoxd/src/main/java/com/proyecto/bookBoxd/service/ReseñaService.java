package com.proyecto.bookBoxd.service;

import com.proyecto.bookBoxd.model.Reseña;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.repository.ReseñaRepository;
import com.proyecto.bookBoxd.repository.UsuarioRepository;
import com.proyecto.bookBoxd.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReseñaService {

    @Autowired
    private ReseñaRepository reseñaRepository;

    // Se inyectan los repositorios de las entidades padre,pq no puede existir sin un usuario ni un libro
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    // Método para guardar una nueva reseña. maneja las relaciones @ManyToOne con Usuario y Libro
    public Reseña saveReseña(Reseña reseña) {
        // Buscamos el Usuario y el Libro por sus IDs en la base de datos.
        // Si no se encuentran, lanzamos una excepción con un mensaje claro.
        Usuario usuario = usuarioRepository.findById(reseña.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + reseña.getUsuario().getId()));
        
        Libro libro = libroRepository.findById(reseña.getLibro().getId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + reseña.getLibro().getId()));

        // cuando ya tenemos las entidades reales de la bdd, las asignamos a la reseña para que la relación sea persistente.
        reseña.setUsuario(usuario);
        reseña.setLibro(libro);
        
        // Guardamos la reseña con las relaciones ya resueltas.
        return reseñaRepository.save(reseña);
    }

    public List<Reseña> findAllReseñas() {
        return reseñaRepository.findAll();
    }

    public Optional<Reseña> findReseñaById(Long id) {
        return reseñaRepository.findById(id);
    }

    public void deleteReseña(Long id) {
        reseñaRepository.deleteById(id);
    }
}