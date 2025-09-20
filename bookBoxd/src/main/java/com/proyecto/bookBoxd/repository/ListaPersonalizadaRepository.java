package com.proyecto.bookBoxd.repository;

import com.proyecto.bookBoxd.model.ListaPersonalizada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaPersonalizadaRepository extends JpaRepository<ListaPersonalizada, Long> {
}