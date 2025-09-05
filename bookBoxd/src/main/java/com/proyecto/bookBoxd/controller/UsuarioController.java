package com.proyecto.bookBoxd.controller; // paquete de controladores

// Importo clases necesarias
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // ->esta clase maneja peticiones HTTP
@RequestMapping("/api/usuarios") // prefijo común para todos los endpoints de usuario
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Crear un nuevo usuario (POST)
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(nuevo);
    }

    // Obtener usuario por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar todos los usuarios (GET)
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Actualizar un usuario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un usuario (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build(); // código 204 sin contenido
    }
}
