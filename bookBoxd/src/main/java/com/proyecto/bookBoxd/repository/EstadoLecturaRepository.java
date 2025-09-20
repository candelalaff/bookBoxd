package com.proyecto.bookBoxd.repository;

import com.proyecto.bookBoxd.model.EstadoLectura;
import com.proyecto.bookBoxd.model.EstadoLecturaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoLecturaRepository extends JpaRepository<EstadoLectura, EstadoLecturaId> {
    
   
}