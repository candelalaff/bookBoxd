package com.proyecto.bookBoxd.mapper;

// --- LOS IMPORTS QUE NECESITO ---
import com.proyecto.bookBoxd.dto.UsuarioDto;
import com.proyecto.bookBoxd.dto.UsuarioCrearDto; // <-- Falta este import para que Java reconozca el tipo en la línea 36
import com.proyecto.bookBoxd.model.Usuario;
import org.springframework.stereotype.Component;

// Mi clase es un @Component. Esto le indica a Spring que la gestione,
// para que pueda inyectarla donde la necesite.
@Component
public class UsuarioMapper {

    // Este es mi método de "traducción". Su función es convertir
    // una entidad `Usuario` en un `UsuarioDto`.
    public UsuarioDto toDto(Usuario usuario) {
        // Primero, una validación simple. Si la entidad es nula, devuelvo nulo.
        if (usuario == null) {
            return null;
        }
        
        // Creo una nueva instancia del DTO.
        UsuarioDto usuarioDto = new UsuarioDto();
        
        // Y aquí copio los datos usando las variables correctas.
        usuarioDto.setId(usuario.getId());
        usuarioDto.setAlias(usuario.getAlias()); 
        usuarioDto.setEmail(usuario.getEmail());
        
        // Finalmente, devuelvo el DTO listo para ser usado.
        return usuarioDto;
    }

    // Este método agarra el DTO que viene del frontend con todos los datos (incluida la contraseña)
    // y me arma un objeto `Usuario` limpito listo para que el servicio lo mande a la base de datos.
    public Usuario toEntity(UsuarioCrearDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setAlias(usuarioDto.getAlias());
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(usuarioDto.getPassword()); // Acá sí mapeo la contraseña porque la necesito para registrarlo

        return usuario;
    }
}