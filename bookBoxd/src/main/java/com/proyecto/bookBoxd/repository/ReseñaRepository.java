package com.proyecto.bookBoxd.repository;

import com.proyecto.bookBoxd.model.Reseña;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<Reseña, Long> proporciona los métodos CRUD para la entidad Reseña.
public interface ReseñaRepository extends JpaRepository<Reseña, Long> {
}