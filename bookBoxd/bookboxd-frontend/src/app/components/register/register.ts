import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';
import { RegisterRequest } from '../../models/auth';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent {

  // Objeto con la estructura de UsuarioCrearDto
  userData: RegisterRequest = {
    alias: '',
    nombre: '',
    apellido: '',
    email: '',
    password: ''
  };

  errorMessage: string = '';
  successMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.authService.register(this.userData).subscribe({
      next: (res) => {
        this.successMessage = '¡Usuario registrado con éxito! Redirigiendo al login...';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (err) => {
        console.error('Error en el registro:', err);
        this.errorMessage = 'No se pudo completar el registro. Verificá los datos ingresados.';
      }
    });
  }
}