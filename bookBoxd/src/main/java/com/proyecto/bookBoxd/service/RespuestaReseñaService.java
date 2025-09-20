package com.proyecto.bookBoxd.service;

import com.proyecto.bookBoxd.model.RespuestaReseña;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.model.Reseña;
import com.proyecto.bookBoxd.repository.RespuestaReseñaRepository;
import com.proyecto.bookBoxd.repository.UsuarioRepository;
import com.proyecto.bookBoxd.repository.ReseñaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.lang.RuntimeException; // Se usa para lanzar excepciones si no se encuentra un recurso.

// La anotación @Service indica que esta clase es un componente de servicio,
// que contiene la lógica de negocio de la aplicación.
@Service
public class RespuestaReseñaService {

    // Inyección de dependencias. @Autowired le dice a Spring que proporcione
    // instancias de los repositorios que necesito para interactuar con la base de datos.
    @Autowired
    private RespuestaReseñaRepository respuestaReseñaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ReseñaRepository reseñaRepository;

    // Guarda una nueva respuesta a una reseña.
    
    public RespuestaReseña saveRespuestaReseña(RespuestaReseña respuesta) {
        // Validación: Busca el usuario y la reseña por su ID.
        // orElseThrow() lanza una excepción si no se encuentran,
        // evitando errores al guardar una respuesta sin las relaciones correctas.
        Usuario usuario = usuarioRepository.findById(respuesta.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Reseña reseña = reseñaRepository.findById(respuesta.getReseña().getId())
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));

        // Se asignan los objetos completos de Usuario y Reseña a la respuesta.
        // Esto asegura que JPA maneje correctamente la relación de las entidades.
        respuesta.setUsuario(usuario);
        respuesta.setReseña(reseña);

        // Llama al método save() del repositorio para persistir la respuesta en la base de datos.
        return respuestaReseñaRepository.save(respuesta);
    }
    
    // Busca una respuesta de reseña por su ID.
   
    public Optional<RespuestaReseña> findById(Long id) {
        // Utiliza el método findById() del JpaRepository para buscar por ID.
        return respuestaReseñaRepository.findById(id);
    }
    
    // Devuelve una lista de todas las respuestas de reseña.
    public List<RespuestaReseña> findAllRespuestas() {
        // Llama al método findAll() del JpaRepository para obtener todas las respuestas.
        return respuestaReseñaRepository.findAll();
    }
    
    // Elimina una respuesta de reseña por su ID.
    public void deleteRespuesta(Long id) {
        // Utiliza el método deleteById() del JpaRepository para eliminar la respuesta.
        respuestaReseñaRepository.deleteById(id);
    }
}