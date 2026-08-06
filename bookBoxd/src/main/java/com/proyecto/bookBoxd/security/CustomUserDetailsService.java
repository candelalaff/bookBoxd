package com.proyecto.bookBoxd.security;

import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

// Servicio personalizado para que Spring Security busque usuarios en mi base de datos
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String alias) throws UsernameNotFoundException {
        // Busco el usuario por su alias en la base de datos
        Usuario usuario = usuarioRepository.findByAlias(alias)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el alias: " + alias));

        // Retorno el objeto UserDetails que entiende Spring Security con sus roles
        return new User(
                usuario.getAlias(),
                usuario.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name()))
        );
    }
}