import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';
import { LibroService } from '../../services/libro';

@Component({
  selector: 'app-libro-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './libro-list.html',
  styleUrl: './libro-list.css'
})
export class LibroListComponent implements OnInit {

  librosGoogle: any[] = [];
  terminoBusqueda: string = 'fantasy';
  cargando: boolean = false;

  constructor(
    private authService: AuthService,
    private libroService: LibroService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarLibros();
  }

  cargarLibros(): void {
    this.cargando = true;
    this.libroService.buscarLibrosGoogle(this.terminoBusqueda).subscribe({
      next: (data: any) => {
        // Obtenemos los items que responde Google Books
        this.librosGoogle = data.items || [];
        this.cargando = false;
      },
      error: (err: any) => {
        console.error('Error al obtener libros de Google:', err);
        this.cargando = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}