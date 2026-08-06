package com.proyecto.bookBoxd.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// Servicio encargado de consumir la API publica de Google Books
@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate;

    public GoogleBooksService() {
        this.restTemplate = new RestTemplate();
    }

    // Consulta la API de Google Books segun el termino de busqueda pasado
    public String buscarLibrosEnGoogle(String query) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=12";
        return restTemplate.getForObject(url, String.class);
    }
}