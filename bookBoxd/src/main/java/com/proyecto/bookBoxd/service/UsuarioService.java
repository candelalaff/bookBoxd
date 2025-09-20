package com.proyecto.bookBoxd.service; // paquete de servicios

// Importo las clases necesarias
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // -> esta clase es un servicio Spring
public class UsuarioService {

    @Autowired // inyección automática del repositorio
    private UsuarioRepository usuarioRepository;

    // Crear un nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario); // guarda el usuario en la BD
    }

    // Obtener un usuario por su ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Actualizar un usuario
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        usuarioActualizado.setId(id); // aseguramos que tenga el mismo ID
        return usuarioRepository.save(usuarioActualizado);
    }

    // Eliminar un usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Buscar por email (ej: para login)
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
