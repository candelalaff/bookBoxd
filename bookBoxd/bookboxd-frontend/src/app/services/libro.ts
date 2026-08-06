import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LibroService {

  private apiUrl = 'http://localhost:8080/api/libros';

  constructor(private http: HttpClient) { }

  // Obtener libros desde la base de datos local
  getLibros(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Buscar libros directamente en la API de Google Books a través de Spring Boot
  buscarLibrosGoogle(query: string = 'fantasy'): Observable<any> {
    const params = new HttpParams().set('q', query);
    return this.http.get<any>(`${this.apiUrl}/buscar`, { params });
  }
}