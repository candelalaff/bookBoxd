package com.proyecto.bookBoxd.service;

import com.proyecto.bookBoxd.model.ListaPersonalizada;
import com.proyecto.bookBoxd.model.Libro;
import com.proyecto.bookBoxd.model.Usuario;
import com.proyecto.bookBoxd.repository.ListaPersonalizadaRepository;
import com.proyecto.bookBoxd.repository.LibroRepository;
import com.proyecto.bookBoxd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;//agrupa los resultados de una operación en una colección (collectors.toset)

@Service
public class ListaPersonalizadaService {

    @Autowired //inyectar dependencias automaticamente
    private ListaPersonalizadaRepository listaPersonalizadaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;
//metodo para guardar una nueva lista o actualizar una existente
    public ListaPersonalizada saveListaPersonalizada(ListaPersonalizada lista) {
        // Busca el usuario por su ID antes de guardar la lista. si no lo encuentra lanza una excepción 
        Usuario usuario = usuarioRepository.findById(lista.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + lista.getUsuario().getId()));
        //asigna el usuario a la lista
        lista.setUsuario(usuario);

        // Si la lista ya contiene libros, los busca y los asigna para asegurar que la relación se guarde correctamente.
        if (lista.getLibros() != null && !lista.getLibros().isEmpty()) {
        	//procesa la coleccion delibros
            lista.setLibros(lista.getLibros().stream()
            		//mapea cada libro en el stream
                .map(libro -> libroRepository.findById(libro.getId())
                		//si no encuentra el libro lanza la excepción
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + libro.getId())))
                //recolecta los resultados en un set, convierte el stream en una colección
                .collect(Collectors.toSet()));
        }
//guarda la lista completa
        return listaPersonalizadaRepository.save(lista);
    }
    //metodo para buscar una lista x id, devuelve un optional que puede estar vacio si no existe la lista
    public Optional<ListaPersonalizada> findListaById(Long id) {
        return listaPersonalizadaRepository.findById(id);
    }

    // metodo findAll para buscar todas las lsitas y devolverlas como una lista
    public List<ListaPersonalizada> findAllListas() {
        return listaPersonalizadaRepository.findAll();
    }
    // Método para agregar un libro a una lista.
    public ListaPersonalizada addLibroToLista(Long listaId, Long libroId) {
        ListaPersonalizada lista = listaPersonalizadaRepository.findById(listaId)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada con ID: " + listaId));

        //busca ell libro por id
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + libroId));

        // Añade el libro al conjunto de libros de la lista y guarda. como es un set evita libros duplicados
        lista.getLibros().add(libro);
      //guarda la lista actualizada en la bdd
        return listaPersonalizadaRepository.save(lista);
    }

    public void deleteLista(Long id) {
        listaPersonalizadaRepository.deleteById(id);
    }
}