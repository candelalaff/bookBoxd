package com.proyecto.bookBoxd.repository; //paquete donde estan los repositorios

import com.proyecto.bookBoxd.model.Usuario;//importo la entidad Usuario
import org.springframework.data.jpa.repository.JpaRepository;//importo jpaRepository
import org.springframework.stereotype.Repository;//lo marco como repositorio  spring

import java.util.Optional;

@Repository 

//jpaRepository <usuario, long> ->esta interfaz maneja entidades usuario con id de tipo long
//save, findById, findAll, deleteteById etc
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
	//metodo personalizado para buscar usuario x email (login o validaciones)
	Optional<Usuario> findByEmail(String email);
    //metodo para buscar x alias
    Optional<Usuario> findByAlias(String alias);
}
