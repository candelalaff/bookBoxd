package com.proyecto.bookBoxd.service;


import com.proyecto.bookBoxd.model.Autor;
import com.proyecto.bookBoxd.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired	// inyecta automáticamente la instancia de AutorRepository ->puede usar sus metodos sin instanciarlos a mano
    private AutorRepository autorRepository;
    
   // Método para guardar o actualizar un autor en la base de datos
    public Autor saveAutor(Autor autor) {
        // Llama al método save() del repositorio para guardar la entidad
        return autorRepository.save(autor);
    }

    // Método para obtener todos los autores de la base de datos
    public List<Autor> findAllAutores() {
        // Llama al método findAll() del repositorio para obtener la lista
        return autorRepository.findAll();
    }

    // Método para buscar un autor por su ID
    public Optional<Autor> findAutorById(Long id) {
        // Llama al método findById() del repositorio, que devuelve un Optional
        return autorRepository.findById(id);
    }

    // Método para eliminar un autor por su ID
    public void deleteAutor(Long id) {
        // Llama al método deleteById() del repositorio
        autorRepository.deleteById(id);
    }
}
