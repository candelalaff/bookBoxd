package com.proyecto.bookBoxd.repository;

import com.proyecto.bookBoxd.model.RespuestaReseña;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaReseñaRepository extends JpaRepository<RespuestaReseña, Long> {
}