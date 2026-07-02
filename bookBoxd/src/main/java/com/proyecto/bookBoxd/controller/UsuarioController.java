package com.proyecto.bookBoxd.controller; // Mi paquete de controladores

// Importo mis DTOs, mi Mapper, mi Modelo y las herramientas de Spring que necesito
import com.proyecto.bookBoxd.dto.UsuarioDto;
import com.proyecto.bookBoxd.dto.UsuarioCrearDto; // Traigo mi nuevo DTO para la creación y actualización
import com.proyecto.bookBoxd.mapper.UsuarioMapper;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // Mi clase es un controlador REST que maneja peticiones HTTP
@RequestMapping("/api/usuarios") // El prefijo común para todas las URLs de mis endpoints de usuarios
public class UsuarioController {

    // Le pido a Spring que me inyecte mi servicio y mi mapper.
    // Esto me permite usar sus métodos sin tener que crear una instancia yo misma.
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioMapper usuarioMapper;

    // --- Mis Endpoints de la API REST ---

    // Este es mi endpoint **`POST`** para crear un nuevo usuario.
    // Ahora está actualizado: en vez de recibir la entidad directa, uso `UsuarioCrearDto` en el `@RequestBody`.
    // Primero traduzco ese DTO a entidad con mi mapper y recién ahí llamo a mi servicio.
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioCrearDto usuarioDto) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        Usuario nuevo = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(nuevo);
    }

    // Este es mi endpoint **`GET`** para obtener un usuario específico por su ID.
    // Con `@PathVariable`, capturo el ID que viene directo en la URL.
    // En lugar de devolver la entidad completa con la contraseña, utilizo mi `mapper` para 
    // pasarla a un `UsuarioDto` y devolverlo de forma segura. Si no lo encuentra, lanzo un 404.
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.map(usuarioMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Este es mi endpoint **`GET`** que lista a todos los usuarios registrados.
    // Traigo la lista desde mi servicio, y con un stream recorro cada elemento para 
    // convertirlo a `UsuarioDto` usando el mapper. Devuelvo la lista limpia con un código 200 OK.
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        List<UsuarioDto> usuariosDto = usuarios.stream()
            .map(usuarioMapper::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDto);
    }

    // Este es mi endpoint **`PUT`** para actualizar los datos de un usuario.
    // Ya lo tengo corregido: ahora recibe el `UsuarioCrearDto` para validar los campos nuevos (como la contraseña).
    // Lo convierto a entidad a través del mapper y llamo al servicio pasándole el ID y los datos transformados.
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioCrearDto usuarioDto) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(actualizado);
    }

    // Este es mi endpoint **`DELETE`** para borrar un usuario por su ID.
    // Este método no necesita usar ningún DTO porque no devuelve datos en el cuerpo.
    // Simplemente le avisa al servicio que elimine el registro y retorna un código 204 No Content.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}