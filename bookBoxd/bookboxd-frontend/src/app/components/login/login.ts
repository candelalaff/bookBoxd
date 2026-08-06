import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';
import { LoginRequest } from '../../models/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  // Modelo vinculado con los campos del formulario
  credentials: LoginRequest = {
    alias: '',
    password: ''
  };

  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // Se ejecuta al hacer submit en el formulario
  onSubmit(): void {
    console.log('--- BOTÓN PRESIONADO ---', this.credentials);
    this.errorMessage = '';
    
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        console.log('Login exitoso, token guardado:', response);
        // Redirige al catálogo de libros al autenticar correctamente
        this.router.navigate(['/libros']);
      },
      error: (err) => {
        console.error('Error en la autenticación:', err);
        this.errorMessage = 'Credenciales inválidas. Revisá tu alias y contraseña.';
      }
    });
  }
}