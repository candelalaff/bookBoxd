package com.proyecto.bookBoxd.controller;

import com.proyecto.bookBoxd.dto.JwtAuthResponseDto;
import com.proyecto.bookBoxd.dto.LoginDto;
import com.proyecto.bookBoxd.dto.UsuarioCrearDto;
import com.proyecto.bookBoxd.model.Rol;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.repository.UsuarioRepository;
import com.proyecto.bookBoxd.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// Controlador REST encargado de exponer las rutas publicas para el alta de usuarios e inicio de sesion
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Endpoint para registrar un nuevo usuario en el sistema
    @PostMapping("/register")
    public ResponseEntity<String> registrarUsuario(@Valid @RequestBody UsuarioCrearDto dto) {

        // Validaciones de existencia previa
        if (usuarioRepository.existsByAlias(dto.getAlias())) {
            return new ResponseEntity<>("El alias ya esta en uso", HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>("El email ya esta registrado", HttpStatus.BAD_REQUEST);
        }

        // Instancio y persisto el nuevo usuario encriptando la contrasena
        Usuario usuario = new Usuario();
        usuario.setAlias(dto.getAlias());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(Rol.ROLE_USER);

        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
    }

    // Endpoint para autenticar usuarios y devolver el token JWT
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> autenticarUsuario(@Valid @RequestBody LoginDto dto) {

        // Intento autenticar las credenciales pasadas
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getAlias(), dto.getPassword())
        );

        // Guardo la autenticacion en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genero el token mediante el JwtTokenProvider
        String token = tokenProvider.generarToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponseDto(token));
    }
}