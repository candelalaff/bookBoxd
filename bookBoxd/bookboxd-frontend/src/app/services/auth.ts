import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse } from '../models/auth';

@Injectable({
  providedIn: 'root' // Permite que este servicio este disponible en toda la aplicacion
})
export class AuthService {

  // Direccion exacta donde esta corriendo nuestro backend en Spring Boot
  // src/app/services/auth.ts
  private apiUrl = 'http://localhost:8080/api/auth'; 
  
  // Nombre de la clave con la que guardaremos el token en el navegador
  private tokenKey = 'jwt_token';

  // Inyectamos HttpClient para poder hacer peticiones HTTP al servidor
  constructor(private http: HttpClient) {}

  // Envia los datos del formulario de registro al backend
  register(userData: RegisterRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/register`, userData, { responseType: 'text' });
  }

  // Envia el alias y password al backend. Si es correcto, guarda el token JWT en localStorage
  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (response && response.accessToken) {
          this.setToken(response.accessToken);
        }
      })
    );
  }

  // Guarda el token en el almacenamiento local del navegador
  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Recupera el token guardado
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Elimina el token (cerrar sesion)
  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  // Verifica si el usuario esta logueado viendo si existe un token
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}