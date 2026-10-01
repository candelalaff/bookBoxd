package com.proyecto.bookBoxd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

// Servicio encargado de consumir la API publica de Google Books
@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate;

    // Tu API key propia, leida desde application.properties (google.books.api.key)
    // Usar una key propia evita compartir la cuota generica/anonima de Google,
    // que se agota muy rapido entre todos los usuarios sin key.
    @Value("${google.books.api.key:}")
    private String apiKey;

    public GoogleBooksService() {
        this.restTemplate = new RestTemplate();
    }

    // Consulta la API de Google Books segun el termino de busqueda pasado.
    // Si Google devuelve error (cuota agotada, rate limit, etc.), no deja que
    // la excepcion explote sin control: devuelve un mensaje claro en su lugar.
    public String buscarLibrosEnGoogle(String query) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=12";

        if (apiKey != null && !apiKey.isBlank()) {
            url += "&key=" + apiKey;
        }

        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException.TooManyRequests e) {
            // Cuota diaria de Google agotada
            return "{\"error\":\"Se alcanzo el limite diario de consultas a Google Books. Probá de nuevo mas tarde.\"}";
        } catch (HttpClientErrorException e) {
            // Cualquier otro error devuelto por Google (403, 400, etc.)
            return "{\"error\":\"No se pudo consultar Google Books en este momento.\"}";
        }
    }
}
