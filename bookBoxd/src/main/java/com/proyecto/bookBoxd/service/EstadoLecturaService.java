package com.proyecto.bookBoxd.service;

import com.proyecto.bookBoxd.model.EstadoLectura;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.model.EstadoLecturaId;
import com.proyecto.bookBoxd.repository.EstadoLecturaRepository;
import com.proyecto.bookBoxd.repository.UsuarioRepository;
import com.proyecto.bookBoxd.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class EstadoLecturaService {

    // Inyección de dependencias: @Autowired le dice a Spring que proporcione
    // instancias de los repositorios que necesito para interactuar con la base de datos.
    @Autowired
    private EstadoLecturaRepository estadoLecturaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LibroRepository libroRepository;

    // Guarda o actualiza un estado de lectura.
    
    public EstadoLectura saveEstadoLectura(EstadoLectura estadoLectura) {
        // Validación: Se asegura de que el usuario exista en la base de datos
        // antes de guardar el estado de lectura. Si no lo encuentra, lanza una excepción.
        Usuario usuario = usuarioRepository.findById(estadoLectura.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Validación similar para el libro.
        Libro libro = libroRepository.findById(estadoLectura.getLibro().getId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Se asignan las instancias completas de Usuario y Libro al objeto EstadoLectura.
        estadoLectura.setUsuario(usuario);
        estadoLectura.setLibro(libro);

        // Llama al método save() del repositorio para persistir el objeto.
        return estadoLecturaRepository.save(estadoLectura);
    }
    
    // Busca un estado de lectura por los IDs de usuario y libro.
     
    public Optional<EstadoLectura> findById(Long usuarioId, Long libroId) {
        // Se crea una instancia de la clase de la clave compuesta (EstadoLecturaId).
        EstadoLecturaId id = new EstadoLecturaId(usuarioId, libroId);
        // Se utiliza el método findById() de JpaRepository que ya sabe cómo buscar
        // por la clave compuesta.
        return estadoLecturaRepository.findById(id);
    }
    
    // Elimina un estado de lectura por los IDs de usuario y libro.
    
    public void deleteEstadoLectura(Long usuarioId, Long libroId) {
        // Se crea una instancia de la clave compuesta.
        EstadoLecturaId id = new EstadoLecturaId(usuarioId, libroId);
        // Se utiliza el método deleteById() de JpaRepository para eliminar por la clave compuesta.
        estadoLecturaRepository.deleteById(id);
    }
}